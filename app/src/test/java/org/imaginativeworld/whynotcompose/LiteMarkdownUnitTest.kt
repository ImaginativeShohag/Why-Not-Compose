/*
 * Copyright 2021 Md. Mahmudul Hasan Shohag
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

package org.imaginativeworld.whynotcompose

import org.imaginativeworld.whynotcompose.utils.LiteMarkdown
import org.junit.Assert.assertEquals
import org.junit.Test

class LiteMarkdownUnitTest {
    @Test
    fun testLiteMarkdown_getSections() {
        var sections: List<Pair<String, Boolean>> = emptyList()

        sections = LiteMarkdown.getSections("The quick brown fox jumps over the lazy dog.")
        assertEquals(1, sections.size) // total sections
        assertEquals(0, sections.filter { it.second }.size) // total code sections

        sections = LiteMarkdown.getSections("The quick `brown` fox jumps `over` the lazy dog.")
        assertEquals(5, sections.size)
        assertEquals(2, sections.filter { it.second }.size)

        sections = LiteMarkdown.getSections("`The quick brown fox jumps over the lazy dog.`")
        assertEquals(1, sections.size)
        assertEquals(1, sections.filter { it.second }.size)

        sections = LiteMarkdown.getSections("`The` quick brown fox jumps over the lazy `dog.`")
        assertEquals(3, sections.size)
        assertEquals(2, sections.filter { it.second }.size)

        sections = LiteMarkdown.getSections("`T`he quick brown fox jumps over the lazy dog`.`")
        assertEquals(3, sections.size)
        assertEquals(2, sections.filter { it.second }.size)

        sections = LiteMarkdown.getSections("The quick` `brown fox jumps `o`ver the lazy dog.")
        assertEquals(5, sections.size)
        assertEquals(2, sections.filter { it.second }.size)

        sections = LiteMarkdown.getSections("The quick brown `fox jumps over the lazy dog.")
        assertEquals(2, sections.size)
        assertEquals(1, sections.filter { it.second }.size)

        sections = LiteMarkdown.getSections("The quick brown fox jumps over the lazy dog.``")
        assertEquals(1, sections.size)
        assertEquals(0, sections.filter { it.second }.size)

        sections = LiteMarkdown.getSections("``The quick brown fox jumps over the lazy dog.")
        assertEquals(1, sections.size)
        assertEquals(0, sections.filter { it.second }.size)

        sections = LiteMarkdown.getSections("The quick brown fox jumps over`` the lazy dog.")
        assertEquals(2, sections.size)
        assertEquals(0, sections.filter { it.second }.size)

        sections = LiteMarkdown.getSections("The quick brown fox jumps over the lazy dog.`")
        assertEquals(1, sections.size)
        assertEquals(0, sections.filter { it.second }.size)

        sections = LiteMarkdown.getSections("`The quick brown fox jumps over the lazy dog.")
        assertEquals(1, sections.size)
        assertEquals(1, sections.filter { it.second }.size)

        sections = LiteMarkdown.getSections("The quick `brown fox` jumps over the `lazy dog`.")
        assertEquals(5, sections.size)
        assertEquals(2, sections.filter { it.second }.size)
    }
}
