/*******************************************************************************
 * Copyright (c) 2008 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Hans Schwaebli - initial API and implementation (Bug 259787)
 *     Toby Weston  - initial API and implementation (Bug 259787)
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.junit;

import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * A runner that captures screenshots on test failures. If you wish to launch your application for your tests use
 * {@link SWTBotApplicationLauncherClassRunner}. Clients are supposed to subclass this. Typical usage is:
 *
 * <pre>
 * &#064;RunWith(SWTBotJunit4ClassRunner.class)
 * public class FooTest {
 * 	&#064;Test
 * 	public void canSendEmail() {
 * 	}
 * }
 * </pre>
 *
 * @author Hans Schwaebli (Bug 259787)
 * @author Toby Weston (Bug 259787)
 * @version $Id$
 * @see SWTBotApplicationLauncherClassRunner
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class SWTBotJunit4ClassRunner extends BlockJUnit4ClassRunner {

	/**
	 * Creates a SWTBotRunner to run {@code klass}
	 *
	 * @throws Exception if the test class is malformed.
	 */
	public SWTBotJunit4ClassRunner(Class<?> klass) throws Exception {
		super(klass);
	}

	public void run(RunNotifier notifier) {
		RunListener failureSpy = new ScreenshotCaptureListener();
		notifier.removeListener(failureSpy); // remove existing listeners that could be added by suite or class runners
		notifier.addListener(failureSpy);
		try {
			super.run(notifier);
		} finally {
			notifier.removeListener(failureSpy);
		}
	}

}
