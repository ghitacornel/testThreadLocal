package manager;

import resource.SpecificThreadResource;

/**
 * THREAD oriented specific resource MANAGER
 */
public class ThreadResourceManager extends ThreadLocal<SpecificThreadResource> {

    @Override
    public SpecificThreadResource initialValue() {
        return new SpecificThreadResource();
    }

}