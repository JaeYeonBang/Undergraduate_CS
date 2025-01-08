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
#include "list_head.h"

///* Declaration for the stack instance defined in pa0.c */
//LIST_HEAD(stack);
//
///* Entry for the stack */
//struct entry {
//	struct list_head list;
//	char* string;
//};
//
//
#include <stdlib.h>                    /* like this */
#include<string.h>
//
// 
// 
// void push_stack(char* string)
// {
// 	struct entry* new_entry = (struct entry*)malloc(sizeof(struct entry));
// 	strcpy(new_entry->string, string);
// 	list_add_tail(&(new_entry->list), &stack);
// 	/* todo: implement this function */
// 
// }
// 
// int pop_stack(char* buffer)
// {
// 	if (!list_empty(&stack)) {
// 		struct entry* temp;
// 		temp = list_entry(&stack, struct entry, list);
// 		strcpy(buffer, temp->string);
// 		list_del(&temp->list);
// 		free(temp);
// 		return 0;
// 	}
// 	else {
// 		/* todo: implement this function */
// 		return -1;/* must fix to return a proper value when @stack is not empty */
// 	}
// }
// 
// 
// void dump_stack(void)
// {
// 	struct entry* ptr;
// 	/* todo: implement this function */
// 	list_for_each_entry(ptr, &stack, list) {
// 		fprintf("%s\n", ptr->string);
// 	}
// 	/* example.
// 										   print out values in this form */
// }
 
 struct people
 {
     int age;
     char name[20];
     struct list_head list;
 };


 int main()
 {
     struct people p1 = { 34, "kim", };

     printf("%d\n", &((struct people*)0)->list);

     printf("name:%s\n",
         ((struct people*)
             ((char*)&p1.list - (int)&((struct people*)0)->list))->name);

    printf("name:%s\n",
         list_entry(&p1.list, struct people, list)->name);

     return 0;
 }

