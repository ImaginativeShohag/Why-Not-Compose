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
                // End of a code section.
                if (isCode) {
                    isCode = false

                    if (currentPart.isNotEmpty()) {
                        tokens.add(Pair(currentPart, true))
                    }
                }
                // Start of a code section.
                else {
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
            // Inside a code section.
            if (isCode) {
                tokens.add(Pair(currentPart, true))
            }
            // Not a code section.
            else {
                tokens.add(Pair(currentPart, false))
            }
        }

        return tokens
    }
}
