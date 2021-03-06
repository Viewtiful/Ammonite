package ammonite.sh

import ammonite.sh2.{PConfig, PPrint}
import utest._

import scala.collection.{immutable => imm}

object PPrintTests extends TestSuite{
  def check[T: PPrint](t: T, expected: String)(implicit pc: PConfig) = {
    val pprinted = PPrint(t)
    assert(pprinted == expected.trim)
  }
  val tests = TestSuite{
    'primitives{
      'Char - {
        * - check('\n', "'\\n'")
        * - check('a', "'a'")
      }
      'Byte - {
        * - check(123.toByte, "123")
        * - check(-123.toByte, "-123")
      }
      'Short - {
        * - check(123.toShort, "123")
        * - check(-12345.toShort, "-12345")
      }
      'Int - {
        * - check(123, "123")
        * - check(-1234567, "-1234567")
      }
      'Long - {
        * - check(123456789012345L, "123456789012345L")
        * - check(-123456789012345L, "-123456789012345L")
      }
      'Float- {
        * - check(0.75F, "0.75F")
        * - check(-13.5F, "-13.5F")
      }
      'Double - {
        * - check(0.125, "0.125")
        * - check(-0.125, "-0.125")
      }
      'String - {
        * - check("i am a cow", """ "i am a cow" """)
        * - check(""" "hello" """.trim, """ "\"hello\"" """)
        * - check("\n\n\n", """ "\n\n\n" """)
      }
      'Symbols - {
        * - check('hello, """'hello""")
        * - check('I_AM_A_COW, """'I_AM_A_COW""")
       }
    }

    'collections{
      'Array - check(Array(1, 2, 3), "Array(1, 2, 3)")
      'Seq - check(Seq(1, 2, 3), "Seq(1, 2, 3)")
      'List - check(List("1", "2", "3"), """List("1", "2", "3")""")
      'Vector - check(Vector('omg, 'wtf, 'bbq), """Vector('omg, 'wtf, 'bbq)""")
      'Stream - check(Stream('omg, 'wtf, 'bbq), """Stream('omg, 'wtf, 'bbq)""")
      'Iterable - check(Iterable('omg, 'wtf, 'bbq), """Iterable('omg, 'wtf, 'bbq)""")
      'Traversable - check(Traversable('omg, 'wtf, 'bbq), """Traversable('omg, 'wtf, 'bbq)""")
      'Set - check(Set('omg), """Set('omg)""")
      'SortedSet- check(
        imm.SortedSet("1", "2", "3"),
        """imm.SortedSet("1", "2", "3")"""
      )
      'Map - check(Map("key" -> "value"), """Map("key" -> "value")""")
      'SortedMap - check(
        imm.SortedMap("key" -> "v", "key2" -> "v2"),
        """imm.SortedMap("key" -> "v", "key2" -> "v2")""")
    }
    'Vertical{
      implicit def pc = ammonite.sh2.PConfig(25)
      'singleNested {
        * - check(
          List("12", "12", "12"),
          """List("12", "12", "12")"""
        )
        * - check(
          List("123", "123", "123"),
          """List("123", "123", "123")"""
        )
        * - check(
          List("1234", "123", "123"),
          """List(
            |  "1234",
            |  "123",
            |  "123"
            |)""".stripMargin
        )
        * - check(
          List("12345", "12345", "12345"),
          """List(
            |  "12345",
            |  "12345",
            |  "12345"
            |)""".stripMargin
        )
      }
      'doubleNested{

        * - check(
          List(Seq("omg", "omg"), Seq("mgg", "mgg"), Seq("ggx", "ggx")),
          """List(
            |  Seq("omg", "omg"),
            |  Seq("mgg", "mgg"),
            |  Seq("ggx", "ggx")
            |)""".stripMargin
        )
        * - check(
          List(Seq("omg", "omg", "omg", "omg"), Seq("mgg", "mgg"), Seq("ggx", "ggx")),
          """List(
            |  Seq(
            |    "omg",
            |    "omg",
            |    "omg",
            |    "omg"
            |  ),
            |  Seq("mgg", "mgg"),
            |  Seq("ggx", "ggx")
            |)""".stripMargin
        )
        * - check(
          List(
            Seq(
              Seq("mgg", "mgg", "lols"),
              Seq("mgg", "mgg")
            ),
            Seq(
              Seq("ggx", "ggx"),
              Seq("ggx", "ggx", "wtfx")
            )
          ),
          """List(
            |  Seq(
            |    Seq(
            |      "mgg",
            |      "mgg",
            |      "lols"
            |    ),
            |    Seq("mgg", "mgg")
            |  ),
            |  Seq(
            |    Seq("ggx", "ggx"),
            |    Seq(
            |      "ggx",
            |      "ggx",
            |      "wtfx"
            |    )
            |  )
            |)""".stripMargin
        )
      }
    }
  }
}