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
#define PUT(p, val) (*(header_t*)(p)=(val))

static LIST_HEAD(free_list); // Don't modify this line
static algo_t g_algo;        // Don't modify this line
static void *bp;             // Don't modify thie line

/***********************************************************************
 *  * extend_heap()
 *   *
 *    * DESCRIPTION
 *     *   allocate size of bytes of memory and returns a pointer to the
 *      *   allocated memory.
 *       *
 *        * RETURN VALUE
 *         *   Return a pointer to the allocated memory.
 *          */

int main() {

	//printf("now : %p\n",sbrk(0));
	header_t* header = sbrk(HDRSIZE);
	void* init = sbrk(0x10000);
	void* new = init + 0x5000;

//	 = false;
	printf("header : %p\n", header);
	printf("init :   %p\n", init);
	printf("new :    %p\n", new);
	printf("new h:   %d\n", (((header_t*)(new))->free = true));

	sbrk(0x10000 + HDRSIZE);
return 0;
}
