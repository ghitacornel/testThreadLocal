package manager;

import resource.Resource;

/**
 * THREAD oriented specific resource MANAGER
 * This MANAGER is usually designed as a SINGLETON
 */
public class ResourceManager extends ThreadLocal<Resource> {

    /*
     * Returns the current thread's "initial value" for this thread-local variable.
     */
    @Override
    public Resource initialValue() {
        return new Resource();
    }

}