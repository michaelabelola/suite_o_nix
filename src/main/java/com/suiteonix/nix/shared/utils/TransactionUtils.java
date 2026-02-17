package com.suiteonix.db.nix.shared.utils;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.function.Supplier;

/**
 * Utility class for transaction-related operations.
 */
public class TransactionUtils {

    /**
     * Executes the given action immediately if no transaction is active
     * or registers it to be executed after the current transaction completes successfully.
     *
     * @param action The action to execute
     */
    public static void executeAfterCommit(Runnable action) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    action.run();
                }
            });
        } else {
            // No transaction is active, execute immediately
            action.run();
        }
    }

    /**
     * Executes the given supplier immediately if no transaction is active
     * or registers it to be executed after the current transaction completes successfully.
     *
     * @param supplier The supplier to execute
     * @param <T> The type of the result
     * @return The result of the supplier, or null if execution is deferred
     */
    public static <T> T executeAfterCommit(Supplier<T> supplier) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    supplier.get();
                }
            });
            return null;
        } else {
            // No transaction is active, execute immediately
            return supplier.get();
        }
    }
}