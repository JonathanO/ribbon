package com.netflix.loadbalancer.reactive;

import com.netflix.loadbalancer.Server;

abstract public class BaseReactiveRule implements IReactiveRule {

    @Override
    public Server choose(Object key) {
        return this.chooser(key).toBlocking().first();
    }
}
