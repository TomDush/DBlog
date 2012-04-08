package fr.dush.test.dblog.engine.context;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import fr.dush.test.dblog.controller.I18nController;
import fr.dush.test.dblog.dao.model.ITicketDAO;

public abstract class ContextedThread extends Thread implements UncaughtExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContextedThread.class);

	protected final ApplicationContext context;

	protected final Locale locale;

	private Throwable throwable;

	public ContextedThread(ApplicationContext context, Locale locale) {
		super();
		this.context = context;
		this.locale = locale;
		setUncaughtExceptionHandler(this);
	}

	@Override
	public final void run() {
		// C'est le controlleur qui détient l'information sur le contexte langue.
		getI18nController().setLocale(locale);

		test();
	}

	public abstract void test();

	protected I18nController getI18nController() {
		return context.getBean(I18nController.class);
	}

	protected ITicketDAO getTicketDAO() {
		return context.getBean(ITicketDAO.class);
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		LOGGER.error("Error in threaded context.", e);
		throwable = e;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	/**
	 * Test si le thread s'est correctement exécuté.
	 *
	 * @return TRUE s'il y a eu une erreur. FALSE si tout s'est bien passé.
	 */
	public boolean hasError() {
		return throwable != null;
	}

	public void startAndWait() throws InterruptedException {
		start();
		join();
	}

	/**
	 * Remonte l'erreur s'il y en a eu une...
	 *
	 * @throws Throwable
	 */
	public void throwError() throws Throwable {
		if (hasError()) {
			LOGGER.error("Thread {} has error for locale {}.", getName(), locale);
			throw new AssertionError("Error for locale " + locale + " in thread " + getName(), throwable);
		}
	}
}
