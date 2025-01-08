/**********************************************************************
 * Copyright (c) 2021
 *  Sang-Hoon Kim <sanghoonkim@ajou.ac.kr>
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

 /*====================================================================*/
 /*          ****** DO NOT MODIFY ANYTHING FROM THIS LINE ******       */
#include <stdio.h>
#include "types.h"
#include "list_head.h"

/* Declaration for the stack instance defined in pa0.c */
extern struct list_head stack;

/* Entry for the stack */
struct entry {
	struct list_head list;
	char* string;
};
/*          ****** DO NOT MODIFY ANYTHING ABOVE THIS LINE ******      */
/*====================================================================*/

/*====================================================================*
 * The rest of this file is all yours. This implies that you can      *
 * include any header files if you want to ...                        */

#include <stdlib.h>                    /* like this */
#include <string.h>
 /**
  * push_stack()
  *
  * DESCRIPTION
  *   Push @string into the @stack. The @string should be inserted into the top
  *   of the stack. You may use either the head or tail of the list for the top.
 */


struct entry* creat_entry(char* buf) {

	struct entry* new_entry;



	new_entry = (struct entry*)malloc(sizeof(struct entry));

	new_entry->string = malloc(sizeof(char) * strlen(buf) + 1);
	strncpy(new_entry->string, buf, strlen(buf));
	new_entry->string[strlen(buf)] = '\0';
	//	new_entry->list = malloc(sizeof(struct list_head));

	return new_entry;
}
void push_stack(char* string)

{
	struct entry* new_entry = creat_entry(string);

	list_add_tail(&new_entry->list, &stack);
	/* TODO: Implement this function */
}

/**
 * pop_stack()
 *
 * DESCRIPTION
 *   Pop a value from @stack and return it through @buffer. The value should
 *   come from the top of the stack, and the corresponding entry should be
 *   removed from @stack.
 *
 * RETURN
 *   If the stack is not empty, pop the top of @stack, and return 0
 *   If the stack is empty, return -1
 */
int pop_stack(char* buffer)
{
	if (!list_empty(&stack)) {
		struct entry* temp;
		temp = list_last_entry(&stack, struct entry, list);
		strcpy(buffer, temp->string);
		list_del(&temp->list);
		free(temp->string);
		free(temp);
		return 0;
	}
	else {
		/* TODO: Implement this function */
		return -1;/* Must fix to return a proper value when @stack is not empty */
	}
	/* TODO: Implement this function */
}


/**
 * dump_stack()
 *
 * DESCRIPTION
 *   Dump the contents in @stack. Print out @string of stack entries while
 *   traversing the stack from the bottom to the top. Note that the value
 *   should be printed out to @stderr to get properly graded in pasubmit.
 */
void dump_stack(void)
{
	struct entry* ptr;
	/* TODO: Implement  this function */
	list_for_each_entry(ptr, &stack, list) {
		fprintf(stderr, "%s\n", ptr->string);

	}
	//	Print out values in this form */
}