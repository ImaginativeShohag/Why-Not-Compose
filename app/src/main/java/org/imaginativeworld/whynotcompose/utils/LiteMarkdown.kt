/*
 * Copyright 2022 Md. Mahmudul Hasan Shohag
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
 * ------------------------------------------------------------------------
 *
 * Project: Why Not Compose!
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Why-Not-Compose
 */

package org.imaginativeworld.whynotcompose.utils

object LiteMarkdown {
    /**
     * Split a [text] into multiple sections.
     * Code sections are separated by pair of backtick (`) characters.
     *
     * Example [text]:
     *
     * > "The quick `brown fox` jumps over the `lazy dog`."
     *
     * Here total section is 5:
     *
     * 1. subText = "The quick ",       isCodeSection = false,
     * 2. subText = "brown fox",        isCodeSection = true
     * 3. subText = " jumps over the ", isCodeSection = false
     * 4. subText = "lazy dog",         isCodeSection = true
     * 5. subText = ".",                isCodeSection = false
     *
     * @return sections from [text] with "is a code section" flag.
     */
    fun getSections(text: String): List<Pair<String, Boolean>> {
        // List of sections with "is code section" flag.
        val tokens = mutableListOf<Pair<String, Boolean>>()

        var currentPart = ""
        var isCode = false

        for (letter in text) {
            if (letter == '`') {
                if (isCode) {
                    // End of a code section.

                    isCode = false

                    if (currentPart.isNotEmpty()) {
                        tokens.add(Pair(currentPart, true))
                    }
                } else {
                    // Start of a code section.

                    isCode = true

                    if (currentPart.isNotEmpty()) {
                        tokens.add(Pair(currentPart, false))
                    }
                }

                currentPart = ""

                continue
            }

            currentPart += letter
        }

        if (currentPart != "") {
            if (isCode) {
                // Inside a code section.
                tokens.add(Pair(currentPart, true))
            } else {
                // Not a code section.
                tokens.add(Pair(currentPart, false))
            }
        }

        return tokens
    }
}
