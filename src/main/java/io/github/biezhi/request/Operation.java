package io.github.biezhi.request;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Operation that handles executing a callback once complete and handling
 * nested exceptions
 *
 * @author biezhi
 * @date 2017/9/30
 */
public abstract class Operation<V> implements Callable<V> {

    /**
     * Run operation
     *
     * @return result
     * @throws RequestException
     * @throws IOException
     */
    protected abstract V run() throws RequestException, IOException;

    /**
     * Operation complete callback
     *
     * @throws IOException
     */
    protected abstract void done() throws IOException;

    public V call() throws RequestException {
        boolean thrown = false;
        try {
            return run();
        } catch (RequestException e) {
            thrown = true;
            throw e;
        } catch (IOException e) {
            thrown = true;
            throw new RequestException(e);
        } finally {
            try {
                done();
            } catch (IOException e) {
                if (!thrown)
                /**
                 * NOTE:
                 *
                 * Basically, finally clauses are there to ensure proper release of a resource.
                 * However, if an exception is thrown inside the finally block, that guarantee goes away.
                 * Worse, if your main block of code throws an exception, the exception raised in the finally block will hide it.
                 * It will look like the error was cause by the call to done, not for the real reason.
                 */
                    throw new RequestException(e);
            }
        }
    }

}
