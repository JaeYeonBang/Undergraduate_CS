/********************************************************:wq**************
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

#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <unistd.h>
#include <errno.h>

#include <string.h>

#include "types.h"
#include "list_head.h"
#include "parser.h"
#include <sys/wait.h>

/***********************************************************************
 * run_command()
 *
 * DESCRIPTION
 *   Implement the specified shell features here using the parsed
 *   command tokens.
 *
 * RETURN VALUE
 *   Return 1 on successful command execution
 *   Return 0 when user inputs "exit"
 *   Return <0 on error
 *
 */
void dump_stack(void);
struct list_head history; 
struct entry {
	struct list_head list;
	char* string;
	int index;
};
int idx = 0;

static void append_history(char * const command);
static int __process_command(char * command);
//static int hist_flag = 0;


static int run_command(int nr_tokens, char *tokens[])
{	int pid, pid2;
	int wstatus;
	int flag = 0;
	int pipe_index = 0;
	if (strcmp(tokens[0], "exit") == 0) return 0;
	int i;
	/// pipe


	


	
	for(i = 0 ; i < nr_tokens ; i++) {
	
		if (strcmp(tokens[i], "|")== 0) {
			pipe_index = 1;
			break;
		}
	}
       if(pipe_index == 1)
	{
		int pipefd[2];
		pipe(pipefd);
		if((pid = fork())== 0) {
			// childi
			close(pipefd[0]);
			dup2(pipefd[1],1);
			close(pipefd[1]);
			char * temp[i];
		for (int j = 0 ; j < i ; j++) {
			temp[j] = tokens[j];
		}
			run_command(i , temp);
			exit(3);
		
		}else {
		// parents
		if((pid2 = fork()) == 0) {
		close(pipefd[1]);
		dup2(pipefd[0],0);
		close(pipefd[0]);		
		char * temp[nr_tokens - i - 1];
		for (int j = i + 1, k = 0 ;j < nr_tokens; j++, k++ ) {
		temp[k] = tokens[j];
		}
		flag = 1;
		run_command(nr_tokens - i - 1 , temp);
		exit(3);
		} else {
		close(pipefd[1]);
		wait(&wstatus);
		wait(&wstatus);
		}
	       	}
	}

	if(pipe_index == 0) {	

	// cd
	if(strcmp(tokens[0], "cd" ) == 0) {
				if (nr_tokens == 1||(strcmp(tokens[1], "~") == 0)){
				chdir(getenv("HOME"));}
					else 	{chdir(tokens[1]);} 
			// parent
		flag = 1;
		
	}
	
	
	else if (strcmp(tokens[0], "history" )== 0) {
	
		if ((pid = fork() == 0)) {

			dump_stack();
			exit(3);
		}
		else {
			wait(&wstatus);
		}
		flag = 1;
	}
	

	else if (strcmp(tokens[0], "!" ) == 0) {
		struct entry* pos = list_first_entry(&history, struct entry, list);
		int from_tokens1 = atoi(tokens[1]);
		for (int i = 0; i < from_tokens1; i++) {
			pos = list_next_entry(pos, list);
		}
		if ((pid = fork() == 0)) {

		char* temp_string = (char*)malloc(sizeof(pos->string));
		strcpy(temp_string, pos->string);
		__process_command(temp_string);
		//parse_command(temp_string, &temp_nr_tokens, temp_tokens);
		//run_command(temp_nr_tokens, temp_dup_tokens);
		/*
		while (1) {
			if (pos->string[size] == (char)(10))
				break;
			size++;
		}
		char* temp_tokens[MAX_NR_TOKENS] = { NULL };
		int temp_nr_tokens = 0;

		
		char ** temp_dup_tokens = (char**)malloc(sizeof(temp_tokens));
		for (int i = 0; i < temp_nr_tokens; i++) {
			temp_dup_tokens[i] = (char*)malloc(sizeof(temp_tokens[i]));
			strcpy(temp_dup_tokens[0], temp_tokens[0]);
		}
		
		
		printf("pos string size%d\n", size);
		if(pos->string[size] != (char)(10)) //&& pos->string[strlen(pos->string)] != (char)(10))
		pos->string[size] = (char)(10);

		printf("pos string size%d\n", strlen(pos->string));
		printf("index : %d\n", pos->index);
		printf("string : %s\n", pos->string);
		printf("\n=========in ! ===========\n");

		struct entry* ptrs1;
		list_for_each_entry(ptrs1, &history, list) {
			for (int i = 0; i < size + 1; i++) {
				printf("%d/", ptrs1->string[i]);
			}
			printf("  %s", ptrs1->string);
			printf("\n");
		}
		printf("\n=========in ! ===========\n");

		

		for (int i = 0; i < temp_nr_tokens; i++) {
			free(temp_dup_tokens[i]);
		}
		free(temp_dup_tokens);*/
		free(temp_string);
		exit(3);
		}
		else {
			wait(&wstatus);
		}
		flag = 1;
	}


	else {	
	
	char * args[nr_tokens + 1];
	for(int i = 0 ; i < nr_tokens ; i++) {
	args[i] = tokens[i];
	}
	args[nr_tokens] = NULL;
	int x = 0;
	if((pid = fork()) == 0) {
		int path_flag = 0;
		for(int i = 0 ; i <(int)(strlen(tokens[0])); i++) {
		if(tokens[0][i] == '/') {
			path_flag = 1;
			break;
			}
		}

		if(path_flag == 1) {x = execv(tokens[0],args);}
		else {
		x = execvp(tokens[0], args);
		}
		if(x != -1) flag = 1;
		if(flag == 0) {fprintf(stderr, "Unable to execute %s\n", tokens[0]);}
		
			exit(3);
	}
		else {
			//parents
			wait(&wstatus);
		}
	}
}
	return -EINVAL;
}
LIST_HEAD(history);

/***********************************************************************
 * struct list_head history
 *
 * DESCRIPTION
 *   Use this list_head to store unlimited command history.
 */
 /* append_history()
 *
 * DESCRIPTION
 *   Append @command into the history. The appended command can be later
 *   recalled with "!" built-in command
 */


static void append_history(char * const command){

	  
	struct entry * new_entry; 

	new_entry = (struct entry * )malloc(sizeof(struct entry));
			
	new_entry->string = malloc(sizeof(char) * strlen(command) + 1);
	strncpy(new_entry->string, command, strlen(command));
	new_entry->string[strlen(command)] = '\0';
	new_entry->index = idx;
	idx++;
	list_add_tail(&new_entry -> list, &history);

}

void dump_stack(void)
{
	struct entry* ptrs;
	/* TODO: Implement  this function */
	list_for_each_entry(ptrs, &history, list) {
		fprintf(stderr, "%2d: %s", ptrs->index, ptrs->string);

	}
	//	Print out values in this form */
}


/***********************************************************************
 * initialize()
 *
 * DESCRIPTION
 *   Call-back function for your own initialization code. It is OK to
 *   leave blank if you don't need any initialization.
 *
 * RETURN VALUE
 *   Return 0 on successful initialization.
 *   Return other value on error, which leads the program to exit.
 */
static int initialize(int argc, char * const argv[])
{
	return 0;
}


/***********************************************************************
 * finalize()
 *
 * DESCRIPTION
 *   Callback function for finalizing your code. Like @initialize(),
 *   you may leave this function blank.
 */
static void finalize(int argc, char * const argv[])
{

}


/*====================================================================*/
/*          ****** DO NOT MODIFY ANYTHING BELOW THIS LINE ******      */
/*          ****** BUT YOU MAY CALL SOME IF YOU WANT TO.. ******      */
static int __process_command(char * command)
{
	char *tokens[MAX_NR_TOKENS] = { NULL };
	int nr_tokens = 0;

	if (parse_command(command, &nr_tokens, tokens) == 0)
		return 1;

	return run_command(nr_tokens, tokens);
}

static bool __verbose = true;
static const char *__color_start = "[0;31;40m";
static const char *__color_end = "[0m";

static void __print_prompt(void)
{
	char *prompt = "$";
	if (!__verbose) return;

	fprintf(stderr, "%s%s%s ", __color_start, prompt, __color_end);
}

/***********************************************************************
 * main() of this program.
 */
int main(int argc, char * const argv[])
{
	char command[MAX_COMMAND_LEN] = { '\0' };
	int ret = 0;
	int opt;

	while ((opt = getopt(argc, argv, "qm")) != -1) {
		switch (opt) {
		case 'q':
			__verbose = false;
			break;
		case 'm':
			__color_start = __color_end = "\0";
			break;
		}
	}

	if ((ret = initialize(argc, argv))) return EXIT_FAILURE;

	/**
	 * Make stdin unbuffered to prevent ghost (buffered) inputs during
	 * abnormal exit after fork()
	 */
	setvbuf(stdin, NULL, _IONBF, 0);

	while (true) {
		__print_prompt();

		if (!fgets(command, sizeof(command), stdin)) break;

		append_history(command);
		ret = __process_command(command);

		if (!ret) break;
	}

	finalize(argc, argv);

	return EXIT_SUCCESS;
}
