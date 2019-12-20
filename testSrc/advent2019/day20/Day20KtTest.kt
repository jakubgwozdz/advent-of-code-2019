package advent2019.day20

import advent2019.expectSetOf
import advent2019.maze.yx
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.expect

internal class Day20KtTest {

    val input1 = """
         A           
         A           
  #######.#########  
  #######.........#  
  #######.#######.#  
  #######.#######.#  
  #######.#######.#  
  #####  B    ###.#  
BC...##  C    ###.#  
  ##.##       ###.#  
  ##...DE  F  ###.#  
  #####    G  ###.#  
  #########.#####.#  
DE..#######...###.#  
  #.#########.###.#  
FG..#########.....#  
  ###########.#####  
             Z       
             Z       """.drop(1).lines()

    @Test
    fun testPart1a() {
        expect(23) { Donut(input1).shortest().sumBy { it.distance } }
    }

    @Test
    fun testPart2a() {
        expect(26) { Donut(input1).shortestRecursive().sumBy { it.distance } }
    }

    @Test
    fun testFindPortals() {
        expectSetOf(
            Portal(2 yx 9, "AA", true),
            Portal(6 yx 9, "BC", false),
            Portal(8 yx 2, "BC", true),
            Portal(10 yx 6, "DE", false),
            Portal(13 yx 2, "DE", true),
            Portal(12 yx 11, "FG", false),
            Portal(15 yx 2, "FG", true),
            Portal(16 yx 13, "ZZ", true)
        ) { Donut(input1).portals }
    }

    @Test
    fun testFindConnection() {
        val aa = Portal(2 yx 9, "AA", true)
        val bc1 = Portal(6 yx 9, "BC", false)
        val bc2 = Portal(8 yx 2, "BC", true)
        val de1 = Portal(10 yx 6, "DE", false)
        val de2 = Portal(13 yx 2, "DE", true)
        val fg1 = Portal(12 yx 11, "FG", false)
        val fg2 = Portal(15 yx 2, "FG", true)
        val zz = Portal(16 yx 13, "ZZ", true)
        val e = setOf(
            Connection(bc1, bc2, 1, 0),
            Connection(de1, de2, 1, 0),
            Connection(fg1, fg2, 1, 0),
            Connection(aa, bc1, 4, 1),
            Connection(aa, fg1, 30, 1),
            Connection(bc2, de1, 6, 1),
            Connection(bc1, fg1, 32, 0),
            Connection(bc1, zz, 28, -1),
            Connection(de2, fg2, 4, 0),
            Connection(fg1, zz, 6, -1),
            Connection(aa, zz, 26, 0)
        )
            .let { s -> s + s.map { c -> Connection(c.portal2, c.portal1, c.distance, -c.levelChange) } }
            .sorted()
        val a = Donut(input1).roads.toSet().sorted()

        assertAll(
            { assertEquals(emptyList(), a - e) },
            { assertEquals(e - a, emptyList()) }
        )

    }

    val input2 = """
                   A               
                   A               
  #################.#############  
  #.#...#...................#.#.#  
  #.#.#.###.###.###.#########.#.#  
  #.#.#.......#...#.....#.#.#...#  
  #.#########.###.#####.#.#.###.#  
  #.............#.#.....#.......#  
  ###.###########.###.#####.#.#.#  
  #.....#        A   C    #.#.#.#  
  #######        S   P    #####.#  
  #.#...#                 #......VT
  #.#.#.#                 #.#####  
  #...#.#               YN....#.#  
  #.###.#                 #####.#  
DI....#.#                 #.....#  
  #####.#                 #.###.#  
ZZ......#               QG....#..AS
  ###.###                 #######  
JO..#.#.#                 #.....#  
  #.#.#.#                 ###.#.#  
  #...#..DI             BU....#..LF
  #####.#                 #.#####  
YN......#               VT..#....QG
  #.###.#                 #.###.#  
  #.#...#                 #.....#  
  ###.###    J L     J    #.#.###  
  #.....#    O F     P    #.#...#  
  #.###.#####.#.#####.#####.###.#  
  #...#.#.#...#.....#.....#.#...#  
  #.#####.###.###.#.#.#########.#  
  #...#.#.....#...#.#.#.#.....#.#  
  #.###.#####.###.###.#.#.#######  
  #.#.........#...#.............#  
  #########.###.###.#############  
           B   J   C               
           U   P   P               """.drop(1).lines()

    @Test
    fun testPart1b() {
        expect(58) { Donut(input2).shortest().sumBy { it.distance } }
    }

    val input3 = """
             Z L X W       C                 
             Z P Q B       K                 
  ###########.#.#.#.#######.###############  
  #...#.......#.#.......#.#.......#.#.#...#  
  ###.#.#.#.#.#.#.#.###.#.#.#######.#.#.###  
  #.#...#.#.#...#.#.#...#...#...#.#.......#  
  #.###.#######.###.###.#.###.###.#.#######  
  #...#.......#.#...#...#.............#...#  
  #.#########.#######.#.#######.#######.###  
  #...#.#    F       R I       Z    #.#.#.#  
  #.###.#    D       E C       H    #.#.#.#  
  #.#...#                           #...#.#  
  #.###.#                           #.###.#  
  #.#....OA                       WB..#.#..ZH
  #.###.#                           #.#.#.#  
CJ......#                           #.....#  
  #######                           #######  
  #.#....CK                         #......IC
  #.###.#                           #.###.#  
  #.....#                           #...#.#  
  ###.###                           #.#.#.#  
XF....#.#                         RF..#.#.#  
  #####.#                           #######  
  #......CJ                       NM..#...#  
  ###.#.#                           #.###.#  
RE....#.#                           #......RF
  ###.###        X   X       L      #.#.#.#  
  #.....#        F   Q       P      #.#.#.#  
  ###.###########.###.#######.#########.###  
  #.....#...#.....#.......#...#.....#.#...#  
  #####.#.###.#######.#######.###.###.#.#.#  
  #.......#.......#.#.#.#.#...#...#...#.#.#  
  #####.###.#####.#.#.#.#.###.###.#.###.###  
  #.......#.....#.#...#...............#...#  
  #############.#.#.###.###################  
               A O F   N                     
               A A D   M                     """.drop(396).lines()


    @Test
    fun testPart2c() {
        expect(26) { Donut(input3).shortestRecursive().sumBy { it.distance } }
    }


}