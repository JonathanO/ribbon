package com.netflix.loadbalancer.reactive;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import rx.Observable;
import rx.Subscriber;

public class ReactiveRuleWrapper extends BaseReactiveRule implements IReactiveRule {

    private IRule rule;

    public ReactiveRuleWrapper(IRule rule) {
        this.rule = rule;
    }

    @Override
    public Observable<Server> chooser(final Object key) {
        return Observable.create(new Observable.OnSubscribe<Server>() {
            @Override
            public void call(Subscriber<? super Server> next) {
                while (!next.isUnsubscribed()) {
                    try {
                        Server server = rule.choose(key);
                        next.onNext(server);
                    } catch (Exception e) {
                        next.onError(e);
                    }
                }
            }
        });
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.rule.setLoadBalancer(lb);
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.rule.getLoadBalancer();
    }
}
