/*
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.java

import org.junit.jupiter.api.Test
import org.openrewrite.Issue

interface RemoveAnnotationTest : JavaRecipeTest {

    @Test
    fun removeAnnotation(jp: JavaParser) = assertChanged(
        jp,
        recipe = RemoveAnnotation("@java.lang.Deprecated"),
        before = """
            import java.util.List;

            @Deprecated
            public class Test {
                @Deprecated
                void test() {
                    @Deprecated int n;
                }
            }
        """,
        after = """
            import java.util.List;

            public class Test {
                void test() {
                    int n;
                }
            }
        """
    )

    @Issue("https://github.com/openrewrite/rewrite/issues/697")
    @Test
    fun preserveWhitespaceOnModifiers(jp: JavaParser) = assertChanged(
        jp,
        recipe = RemoveAnnotation("@java.lang.Deprecated"),
        before = """
            import java.util.List;

            @Deprecated
            public class Test {
                @Deprecated
                private final Integer value = 0;
            }
        """,
        after = """
            import java.util.List;

            public class Test {
                private final Integer value = 0;
            }
        """
    )
}
