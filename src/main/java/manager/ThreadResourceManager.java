package manager;

import resource.SpecificThreadResource;

/**
 * THREAD oriented specific resource MANAGER<br>
 * It is usually designed as a singleton
 */
public class ThreadResourceManager extends ThreadLocal<SpecificThreadResource> {

    @Override
    public SpecificThreadResource initialValue() {
        return new SpecificThreadResource();
    }

}