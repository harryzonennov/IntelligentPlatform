package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.common.model.LogonInfo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ServiceAsyncExecuteProxy {

    /**
     * Using Async way to update production order post tasks.
     *
     * @return
     * @throws ServiceEntityExceptionContainer
     */
    public static <T, R> void executeAsyncWrapper(
            T inputPara, IComSimExecutor<T, R> coreExecutor, LogonInfo logonInfo)
            throws ServiceEntityExceptionContainer {
//        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletableFuture<ServiceEntityExceptionContainer> serviceEntityExceptionFuture = new CompletableFuture<>();
        CompletableFuture<R> future = CompletableFuture.supplyAsync(() -> {
            try {
                return coreExecutor.execute(inputPara, logonInfo);
            } catch (ServiceEntityExceptionContainer e) {
                serviceEntityExceptionFuture.complete(e);
                throw new CompletionException(e);
            }
        }).thenApply(result->{
            return result;
        }).handle((result, ex)->{
            if (null != ex) {
                throw new CompletionException(ex);
            } else {
                return result;
            }
        });
//        R result = null;
//        try {
//            result = future.join();
//        } catch (CompletionException ex) {
//            if (serviceEntityExceptionFuture.isDone()) {
//                throw serviceEntityExceptionFuture.join();
//            }
//            throw ex;
//        }
//        return result;
    }
}
