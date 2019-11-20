package manager;

import resource.SpecificThreadResource;

/**
 * THREAD oriented specific resource MANAGER<br>
 * It is usually designed as a SINGLETON
 */
public class ResourceManager extends ThreadLocal<SpecificThreadResource> {

    @Override
    public SpecificThreadResource initialValue() {
        return new SpecificThreadResource();
    }

}