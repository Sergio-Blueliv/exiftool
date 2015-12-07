/**
 * Copyright 2011 The Buzz Media, LLC
 * Copyright 2015 Mickael Jeanroy <mickael.jeanroy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thebuzzmedia.exiftool.logs;

import com.thebuzzmedia.exiftool.commons.reflection.ClassUtils;

/**
 * Factory to use to create {@link com.thebuzzmedia.exiftool.logs.Logger} instances.
 * Appropriate implementation will be used depending on classpath.
 * Verification is done in the following order:
 * - If slf4j is defined, then it will be used.
 * - If log4j is defined, it will be used.
 * - Finally, instance of {@link com.thebuzzmedia.exiftool.logs.DefaultLogger} is used.
 */
public final class LoggerFactory {

	// Ensure non instantiation.
	private LoggerFactory() {
	}

	/**
	 * Return a logger named corresponding to the class passed as parameter,
	 *
	 * @param klass the returned logger will be named after clazz.
	 * @return Logger implementation.
	 */
	public static Logger getLogger(Class<?> klass) {
		// First try slf4j
		if (ClassUtils.isPresent("org.slf4j.Logger")) {
			org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(klass);
			return new LoggerSlf4j(log);
		}

		// Then, try log4j
		if (ClassUtils.isPresent("org.apache.log4j.Logger")) {
			org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(klass);
			return new LoggerLog4j(log);
		}

		// Return default logger...
		return new DefaultLogger(Boolean.getBoolean("exiftool.debug"));
	}
}
