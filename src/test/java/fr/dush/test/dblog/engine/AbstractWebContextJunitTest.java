package fr.dush.test.dblog.engine;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * ??
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 * @deprecated Devrai permettre de faire des JUNIT sur la gestion des sessions/request.
 */
@Deprecated
public abstract class AbstractWebContextJunitTest extends AbstractJunitTest {

	protected MockHttpSession session;

	protected MockHttpServletRequest request;

	protected void startSession() {
		session = new MockHttpSession();
	}

	protected void endSession() {
		session.clearAttributes();
		session = null;
	}

	protected void startRequest() {
		request = new MockHttpServletRequest();
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	protected void endRequest() {
		((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).requestCompleted();
		RequestContextHolder.resetRequestAttributes();
		request = null;
	}
}
