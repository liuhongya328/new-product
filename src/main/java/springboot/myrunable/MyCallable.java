package springboot.myrunable;

/**
 * @author liuhongya328
 *
 */
public interface MyCallable<V> {

	V call() throws Exception;

}
