package manager;

import resource.Resource;

/**
 * THREAD oriented specific resource MANAGER<br>
 * It is usually designed as a SINGLETON
 */
public class ResourceManager extends ThreadLocal<Resource> {

    @Override
    public Resource initialValue() {
        return new Resource();
    }

}