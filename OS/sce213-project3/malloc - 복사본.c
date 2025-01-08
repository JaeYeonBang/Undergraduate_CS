/**********************************************************************
 * Copyright (c) 2020
 *  Jinwoo Jeong <jjw8967@ajou.ac.kr>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTIABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 **********************************************************************/

#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdint.h>
#include <stdint.h>

#include "malloc.h"
#include "types.h"
#include "list_head.h"

#define ALIGNMENT 32
#define HDRSIZE sizeof(header_t)

static LIST_HEAD(free_list); // Don't modify this line
static algo_t g_algo;        // Don't modify this line
static void *bp;             // Don't modify thie line

/***********************************************************************
 * extend_heap()
 *
 * DESCRIPTION
 *   allocate size of bytes of memory and returns a pointer to the
 *   allocated memory.
 *
 * RETURN VALUE
 *   Return a pointer to the allocated memory.
 */


void *my_malloc(size_t size)
{
    bool found_empty = false;
    size_t blocks;

    //// get number of blocks
    if ((size) % ALIGNMENT == 0) blocks = (size) / ALIGNMENT;
    else blocks = (size) / ALIGNMENT + 1;
    header_t* empty_header = NULL;
    header_t* header = NULL;
    size_t best_size = UINT32_MAX;
    if (g_algo == FIRST_FIT) {
        list_for_each_entry(header, &free_list, list) {
            if (header->size >= size && header->free) { 
                empty_header = header;
                found_empty = true;
                break; 
            }
        }
    }
    else if (g_algo == BEST_FIT) {

        list_for_each_entry(header, &free_list, list) { 
            if (header->size >= size && header->free && header->size < best_size) {
                best_size = header->size;
                empty_header = header;
                found_empty = true;
            } 
        }
    }

    if (!found_empty) {     /// not found empty
        printf("not found empty\n");
        header_t* new_header = sbrk(HDRSIZE);
        new_header->free = false;
        new_header->size = ALIGNMENT * blocks;
        list_add_tail(&new_header->list, &free_list);
        void * temp = sbrk(ALIGNMENT * blocks);
        bp = sbrk(0);
        return temp;
    }
    else {  // found empty
        printf("found empty\n");
        empty_header->free = false;
        if (empty_header->size > ALIGNMENT * blocks) {
            header_t* new_header = sbrk(HDRSIZE);
            new_header->free = true;
            new_header->size = empty_header->size - ALIGNMENT * blocks - HDRSIZE;
            list_add(&new_header->list, &empty_header->list);
            empty_header->size = ALIGNMENT * blocks;
        }
     
    }

  return NULL;
}

/***********************************************************************
 * my_realloc()
 *
 * DESCRIPTION
 *   tries to change the size of the allocation pointed to by ptr to
 *   size, and returns ptr. If there is not enough memory block,
 *   my_realloc() creates a new allocation, copies as much of the old
 *   data pointed to by ptr as will fit to the new allocation, frees
 *   the old allocation.
 *
 * RETURN VALUE
 *   Return a pointer to the reallocated memory
 */
void *my_realloc(void *ptr, size_t size)
{
    header_t* header = ptr - HDRSIZE;
    
    if(header->size >= size) 
    

  /* Implement this function */
  return NULL;
}

/***********************************************************************
 * my_realloc()
 *
 * DESCRIPTION
 *   deallocates the memory allocation pointed to by ptr.
 */
void my_free(void *ptr)
{
    header_t* header = ptr - HDRSIZE;
    header_t* cursor;
    header_t* cursor2;
    if (header == list_first_entry(&free_list, header_t, list)) {    //맨 앞임
        cursor = list_next_entry(header, list);
        if (!cursor->free) {
            header->free = true;    /// 맨 앞인데, 뒤에가 비어 있지 않음
            return;
        }
        else {  // 맨 앞인데 뒤에가 비어있음
            header->free = true;
            header->size = header->size + cursor->size + HDRSIZE;
            list_del(&cursor->list);
            return;
        }
    }

    else if (header == list_last_entry(&free_list, header_t, list)) { // 맨 뒤임
        cursor = list_prev_entry(header , list);
        if (!cursor->free) {        /// 맨 뒤인데 앞이 차있음
            header->free = true;    
            return;
        }
        else {
            cursor->free = true;
            cursor->size = header->size + cursor->size + HDRSIZE;
            list_del(&header->list);
            return;
        }
    }

    else {  /// 중간임
        cursor = list_prev_entry(header, list);
        cursor2 = list_next_entry(header, list);

        if (!cursor->free && !cursor2->free) header->free = true; // 앞 뒤로 차있음
        else if (!cursor->free && cursor2->free) {  // 앞에만 차있음 , 뒤는 빔
            header->free = true;
            header->size = header->size + cursor2->size + HDRSIZE;
            list_del(&cursor2->list);
        }
        else if (cursor->free && !cursor2->free) {  // 앞은 비어잇음 , 뒤는 차있음
        
            cursor->free = true;
            cursor->size = header->size + cursor->size + HDRSIZE;
            list_del(&header->list);
            return;
        }
        else if (cursor->free && cursor2->free){          //앞 뒤 다 비어있음
            cursor->free = true;
            cursor->size = header->size + cursor->size + cursor2->size + 2 * HDRSIZE;
            list_del(&header->list);
            list_del(&cursor2->list);
        }

    }

  /* Implement this function */
  return;
}

/*====================================================================*/
/*          ****** DO NOT MODIFY ANYTHING BELOW THIS LINE ******      */
/*          ****** BUT YOU MAY CALL SOME IF YOU WANT TO.. ******      */
/*          ****** EXCEPT TO mem_init() AND mem_deinit(). ******      */
void mem_init(const algo_t algo)
{
  g_algo = algo;
  bp = sbrk(0);
}

void mem_deinit()
{
  header_t *header;
  size_t size = 0;
  list_for_each_entry(header, &free_list, list) {
    size += HDRSIZE + header->size;
  }
  sbrk(-size);

  if (bp != sbrk(0)) {
    fprintf(stderr, "[Error] There is memory leak\n");
  }
}

void print_memory_layout()
{
  header_t *header;
  int cnt = 0;

  printf("===========================\n");
  list_for_each_entry(header, &free_list, list) {
    cnt++;
    printf("%c %ld\n", (header->free) ? 'F' : 'M', header->size);
  }

  printf("Number of block: %d\n", cnt);
  printf("===========================\n");
  return;
}
