A java.lang.ThreadLocal allows linking / un-linking of any kind of object to an externally controlled java.lang.Thread.
The linked object must be seen as a specific thread resource linked to a specific business workflow ( execution context ).
Examples of execution contexts : business transactions, database transactions, security contexts data, user request context data.
java.lang.ThreadLocal can be used when the running thread actual runnable class cannot be controlled or defined
thus the running thread cannot be linked with a custom running context via a simple class field reference.


A java.lang.ThreadLocal acts as a java.util.Map < java.lang.ref.WeakReference < java.lang.Thread > , java.lang.Object >
A java.lang.ThreadLocal always uses the current thread object as map key for all operations performed.
Current thread is obtained via java.lang.Thread.currentThread.
It is the responsibility of the developer to ensure proper thread resource linking and un-linking.


See :

- http://tutorials.jenkov.com/java-concurrency/threadlocal.html
- java.lang.ThreadLocal

Exercise :

Test a ThreadLocal on a single thread
Test a ThreadLocal over a couple of running in parallel threads.

Info :

Application servers maintain a pool of threads used by the deployed applications
The pool of threads is managed by the application server only
For each user request the application server allocates a thread to process the user request
The application server links a user request context to the processing thread
The application server un-links the user request context to the processing thread once the user request is processed

