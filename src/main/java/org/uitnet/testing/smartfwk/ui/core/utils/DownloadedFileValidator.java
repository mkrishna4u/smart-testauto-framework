/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.uitnet.testing.smartfwk.ui.core.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class DownloadedFileValidator {
	private DownloadedFileValidator() {
		// do nothing
	}

	public static void validateFileDownloadedOnPath(String fileName, String downloadDir,
			TextMatchMechanism fileNameMatchMechanism, String regEx, boolean deleteAfterValidation) {
		TextMatchMechanism fnMatchMech = null;
		if (fileNameMatchMechanism == null) {
			fnMatchMech = TextMatchMechanism.exactMatchWithExpectedValue;
		}

		try {

			Stream<Path> files = Files.list(Path.of(downloadDir));

			switch (fnMatchMech) {
			case exactMatchWithExpectedValue:

				break;
			case containsExpectedValue:

				break;
			case endsWithExpectedValue:

				break;
			case startsWithExpectedValue:

				break;
			case exactMatchWithExpectedValueWithRemovedWhiteSpace:

				break;
			case matchWithRegularExpression:

				break;

			default:
				break;
			}
		} catch (Exception e) {

		} finally {

		}
	}
}
