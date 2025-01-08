#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/time.h>
#include <ucontext.h>
#include "uthread.h"
#include "list_head.h"
#include "types.h"

/* You can include other header file, if you want. */

/*******************************************************************
 * struct tcb
 *
 * DESCRIPTION
 *    tcb is a thread control block.
 *    This structure contains some information to maintain thread.
 *
 ******************************************************************/


struct tcb {
    struct list_head list;
    ucontext_t* context;
    enum uthread_state state;
    int tid;

    int lifetime; // This for preemptive scheduling
    int priority; // This for priority scheduling
};

/***************************************************************************************
 * LIST_HEAD(tcbs);
 *
 * DESCRIPTION
 *    Initialize list head of thread control block.
 *
 **************************************************************************************/
LIST_HEAD(tcbs);
struct tcb* old = NULL;
struct tcb* next = NULL;
struct tcb* current_tcb, * MAIN_TCB;
int n_tcbs = 0;
enum uthread_sched_policy thread_policy;
struct ucontext_t* t_context;
struct ucontext_t* temp_context;
int pre = 0;
//

//sigprocmask(SIG_BLOCK, &mask, NULL);
void dump() {

    struct tcb* temp;

    list_for_each_entry(temp, &tcbs, list) {
        if (temp != NULL) {
            //printf("\temp tid : %d\n", temp->tid);
            //printf("\temp state : %d\n", temp->state);
            //printf("\temp next  : %d\n", temp->context->uc_link->uc_stack);
            //printf("\n");
        }
    }
    temp = NULL;
}
/***************************************************************************************
 * next_tcb()
 *
 * DESCRIPTION
 *
 *    Select a tcb with current scheduling policy
 *
 **************************************************************************************/
sigset_t mask;

//void signalhandler(int sig){
//    if (sig == SIGALRM) {
//        sigprocmask(SIG_BLOCK, &mask, NULL);
//        current_tcb->lifetime = current_tcb->lifetime - 1;
//        current_tcb->state = READY;
//        if (current_tcb->lifetime <= 0) current_tcb->state = TERMINATED;
//        fprintf(stderr, "life time : %d , tid = %d\n", current_tcb->lifetime, current_tcb->tid);
//        if (current_tcb->lifetime <= 0) current_tcb->state = TERMINATED;
//        fprintf(stderr, "current tid : %d , main tid = %d\n",  current_tcb->tid, MAIN_TCB->tid);
//        //swapcontext(current_tcb->context, MAIN_TCB->context);
//    }
//}
void next_tcb() {
    //sigprocmask(SIG_BLOCK, &mask, NULL);
    //sigprocmask(SIG_UNBLOCK, &mask, NULL);
    void (*hand)(int);
    //fprintf(stderr, "in next\n");
    if (thread_policy == FIFO) {

        next = fifo_scheduling(current_tcb);
    }
    else if (thread_policy == RR) {
        //fprintf(stderr, "in RR  \n");
        //next = rr_scheduling(current_tcb);
        //fprintf(stderr,"tid : %d\n", next->tid);
        //sigprocmask(SIG_UNBLOCK, &mask, NULL);
        
    }
    else if (thread_policy == PRIO) {
        //sigset_t mask;
        //sigaddset(&mask, SIGALRM);
        //sigprocmask(SIG_UNBLOCK, &mask, NULL);
        //next = prio_scheduling(next);
    }
    else if (thread_policy == SJF) {

        sigprocmask(SIG_BLOCK, &mask, NULL);
        next = sjf_scheduling(next);
    }

    if (current_tcb == next) {
        sigprocmask(SIG_BLOCK, &mask, NULL);
    }

    else if (current_tcb != next) {

        //current_tcb->state = READY;
        MAIN_TCB->state = READY;
        next->state = RUNNING;

        old = current_tcb;
        current_tcb = next;
        fprintf(stderr, "SWAP %d -> %d\n", old->tid, current_tcb->tid);
        //signal(SIGALRM, signalhandler);
        //if(thread_policy == RR || thread_policy == PRIO) sigprocmask(SIG_UNBLOCK, &mask, NULL);
        
        //fprintf(stderr, "states %d -> %d\n", old->state, current_tcb->state);
        swapcontext(old->context, current_tcb->context);
        //fprintf(stderr, "after swap\n");
    }
   
    /* TODO: You have to implement this function. */

}


/***************************************************************************************
 * struct tcb *fifo_scheduling(struct tcb *next)
 *
 * DESCRIPTION
 *
 *    This function returns a tcb pointer using First-In-First-Out policy
 *
 **************************************************************************************/
struct tcb* fifo_scheduling(struct tcb* next) {
    struct tcb* temp = NULL;
    if (n_tcbs == 1) {
        // main밖에 없을때
        temp = list_first_entry(&tcbs, struct tcb, list);
        //next = temp;
        //printf("in == 1 from fifo , tid = %d\n", next->tid);
        return temp;
    }

    else if (n_tcbs > 1) {
        // main말고 있을 때, 다른 thread의 state 확인
        list_for_each_entry(temp, &tcbs, list) {
            //printf("%d state  : %d\n", temp->tid, temp->state);
            if ((temp != NULL) && (temp->tid != -1) && (temp->state == READY)) {
                //printf("%d state  : %d\n", temp->tid, temp->state);
                return temp;
            }
        }
    }
    return list_first_entry(&tcbs, struct tcb, list);

}
//


//return temp;
/* TODO: You have to implement this function. */


/***************************************************************************************
 * struct tcb *rr_scheduling(struct tcb *next)
 *
 * DESCRIPTION
 *
 *    This function returns a tcb pointer using round robin policy
 *
 **************************************************************************************/
struct tcb* rr_scheduling(struct tcb* next) {
    //fprintf(stderr , "\n===== RR====\n");
    struct tcb* temp = next;
    struct tcb* last_entry = list_last_entry(&tcbs, struct tcb, list);
    if (n_tcbs <= 1) {
        // main밖에 없을때
        temp = list_first_entry(&tcbs, struct tcb, list);
        //next = temp;
        //printf("in == 1 from fifo , tid = %d\n", next->tid);
        return temp;
    }
    
    else {
        for (int i = 0; i < n_tcbs; i++) {
            if(temp->tid == last_entry->tid) temp = list_next_entry(list_first_entry(&tcbs, struct tcb, list), list);
            else temp = list_next_entry(temp, list);
            //fprintf(stderr, "tid : %d , lifetime = %d , state : %d\n", temp->tid, temp->lifetime, temp->state);
            if ((temp != NULL) && (temp->tid != -1) && (temp->state == READY) && (temp->lifetime > 0)) return temp;
        }
        temp = list_first_entry(&tcbs, struct tcb, list);
        return  temp;
     }
    temp = list_first_entry(&tcbs, struct tcb, list);
    return  temp;
    /* TODO: You have to implement this function. */

}

/***************************************************************************************
 * struct tcb *prio_scheduling(struct tcb *next)
 *
 * DESCRIPTION
 *
 *    This function returns a tcb pointer using priority policy
 *
 **************************************************************************************/
struct tcb* prio_scheduling(struct tcb* next) {
    

    /* TODO: You have to implement this function. */

}

/***************************************************************************************
 * struct tcb *sjf_scheduling(struct tcb *next)
 *
 * DESCRIPTION
 *
 *    This function returns a tcb pointer using shortest job first policy
 *
 **************************************************************************************/
struct tcb* sjf_scheduling(struct tcb* next) {

    struct tcb* shortest = list_first_entry(&tcbs, struct tcb, list);
    struct tcb* temp = NULL;
    list_for_each_entry(temp, &tcbs, list) {
        //printf("%d state  : %d\n", temp->tid, temp->state);
        if ((temp != NULL) && (temp->tid != -1) && (temp->state == READY)) {
            
            if (temp->lifetime < shortest->lifetime) shortest = temp;
                //printf("%d state  : %d\n", temp->tid, temp->state);
        }
    }

    return shortest;
    /* TODO: You have to implement this function. */

}

/***************************************************************************************
 * uthread_init(enum uthread_sched_policy policy)
 *
 * DESCRIPTION

 *    Initialize main tread control block, and do something other to schedule tcbs
 *
 **************************************************************************************/
void uthread_init(enum uthread_sched_policy policy) {
    /* TODO: You have to implement this function. */

    char stack[MAX_STACK_SIZE];

    // main tcb 만글기 
    struct tcb* main_tcb = malloc(sizeof(struct tcb));
    list_add_tail(&main_tcb->list, &tcbs);      // 일단은 스택으로 넣음
    main_tcb->context = malloc(sizeof(struct ucontext_t));
    //getcontext(main_tcb->context);
    //main_tcb->context = t_context;
    //main_tcb->context->uc_link = NULL;
    main_tcb->state = RUNNING;
    main_tcb->tid = MAIN_THREAD_TID;
    main_tcb->lifetime = MAIN_THREAD_LIFETIME;
    main_tcb->priority = MAIN_THREAD_PRIORITY;
    sigaddset(&mask, SIGALRM);
    n_tcbs++;
    
    current_tcb = main_tcb;
    MAIN_TCB = main_tcb;
    if (policy == FIFO) {
        thread_policy = FIFO;
        pre = 0;
    }
    else if (policy == RR) {
        thread_policy = RR;
        pre = 1;
        fprintf(stderr , "RR signal handle\n");
       
    }
    else if (policy == PRIO) {
        pre = 1;
        thread_policy = PRIO;

    }
    else if (policy == SJF) {
        thread_policy = SJF;
        pre = 0;
    }

    /* DO NOT MODIFY THESE TWO LINES */
    __create_run_timer();
    __initialize_exit_context();
}


/***************************************************************************************
 * uthread_create(void* stub(void *), void* args)
 *
 * DESCRIPTION
 *
 *    Create user level thread. This function returns a tid.
 *
 **************************************************************************************/
int uthread_create(void* stub(void*), void* args) {

    
    __initialize_exit_context();
    struct tcb* new_tcb = malloc(sizeof(struct tcb));
    list_add_tail(&new_tcb->list, &tcbs);      // 일단은 스택으로 넣음

    new_tcb->context = malloc(sizeof(struct ucontext_t));
    getcontext(new_tcb->context);

    new_tcb->context->uc_stack.ss_sp = malloc(MAX_STACK_SIZE);
    new_tcb->context->uc_stack.ss_size = MAX_STACK_SIZE;
    new_tcb->context->uc_stack.ss_flags = 0;
    if(!pre) new_tcb->context->uc_sigmask = mask;
    //printf("%d", t_context->uc_link);
    new_tcb->context->uc_link = t_context; //

    makecontext(new_tcb->context, (void*)stub, 0);
    //printf("%d\n", &new_tcb->context->uc_stack.ss_sp);
    new_tcb->tid = ((int*)args)[0];
    new_tcb->lifetime = ((int*)args)[1];
    new_tcb->priority = ((int*)args)[2];
    new_tcb->state = READY;

    n_tcbs++;

    //printf("create %d\n", new_tcb->tid);
    //printf("%d\n", n_tcbs);
    //__initialize_exit_context();
    //setcontext(t_context);

    //dump();
    //printf("creat %d\n", new_tcb->tid);
    return new_tcb->tid;
    /* TODO: You have to implement this function. */
}

/***************************************************************************************
 * uthread_join(int tid)
 *
 * DESCRIPTION
 *
 *    Wait until thread context block is terminated.
 *
 **************************************************************************************/
void uthread_join(int tid) {
    // dump();

    struct tcb* temp;
    list_for_each_entry(temp, &tcbs, list) {
        if (temp != NULL && temp->tid != -1 && temp->tid == tid) {
            while (temp->state != TERMINATED);
            //temp->state == TERMINATED;
            fprintf(stderr, "JOIN %d\n", temp->tid);
            list_del(&temp->list);
            free(temp->context->uc_stack.ss_sp);
            free(temp->context);
            free(temp);
            n_tcbs--;
            break;

        }
    }

    //dump();

    /* TODO: You have to implement this function. */

}

/***************************************************************************************
 * __exit()
 *
 * DESCRIPTION
 *
 *    When your thread is terminated, the thread have to modify its state in tcb block.
 *
 **************************************************************************************/
void __exit() {
    struct tcb* temp;
    temp = list_first_entry(&tcbs, struct tcb, list);
    //fprintf(stderr, "exit\n");
    current_tcb->state = TERMINATED;
    //sigset_t mask;
    //sigaddset(&mask, SIGALRM);
    //sigprocmask(SIG_UNBLOCK, &mask, NULL);
    //printf("in exit current pid : %d state : %d\n", current_tcb->tid, current_tcb->state);
    //printf("dump in exit\n\n");
    //dump();
    //printf("##########################3\n");
    //printf("go main\n");
    //sigset_t mask;
    //sigaddset(&mask, SIGALRM);
    //sigprocmask(SIG_UNBLOCK, &mask, NULL);
    //setcontext(MAIN_TCB->context);
    /* TODO: You have to implement this function. */
    //if (thread_policy == FIFO) {
    //    temp = fifo_scheduling(temp);
    //}
    //else if (thread_policy == RR) {
    //    temp = rr_scheduling(temp);
    //}
    //else if (thread_policy == PRIO) {
    //    temp = prio_scheduling(temp);
    //}
    //else if (thread_policy == SJF) {
    //    temp = sjf_scheduling(temp);
    //}
    //setcontext(temp->context);
}

/***************************************************************************************
 * __initialize_exit_context()
 *
 * DESCRIPTION
 *
 *    This function initializes exit context that your thread context will be linked.
 *
 **************************************************************************************/
void __initialize_exit_context() {

    //    char terstack[MAX_STACK_SIZE];

    t_context = malloc(sizeof(struct ucontext_t));
    getcontext(t_context);
    //t_context->uc_stack.ss_flags = 0;
    t_context->uc_stack.ss_sp = malloc(MAX_STACK_SIZE);
    t_context->uc_stack.ss_size = MAX_STACK_SIZE;

    t_context->uc_link = MAIN_TCB->context;

    makecontext(t_context, (void*)__exit, 0);
    /* TODO: You have to implement this function. */

}

/***************************************************************************************
 *
 * DO NOT MODIFY UNDER THIS LINE!!!
 *
 **************************************************************************************/

static struct itimerval time_quantum;
static struct sigaction ticker;

void __scheduler() {
    if (n_tcbs > 1)

        next_tcb();
}

void __create_run_timer() {

    time_quantum.it_interval.tv_sec = 0;
    time_quantum.it_interval.tv_usec = SCHEDULER_FREQ;
    time_quantum.it_value = time_quantum.it_interval;

    ticker.sa_handler = __scheduler;
    sigemptyset(&ticker.sa_mask);
    sigaction(SIGALRM, &ticker, NULL);
    ticker.sa_flags = 0;

    setitimer(ITIMER_REAL, &time_quantum, (struct itimerval*)NULL);
}

void __free_all_tcbs() {
    struct tcb* temp;

    list_for_each_entry(temp, &tcbs, list) {
        if (temp != NULL && temp->tid != -1) {
            list_del(&temp->list);
            free(temp->context);
            free(temp);
            n_tcbs--;
            temp = list_first_entry(&tcbs, struct tcb, list);
        }
    }
    temp = NULL;
    free(t_context);
}
