/*
 * Copyright (C) 2022 Kevin Zatloukal and James Wilcox.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package sets;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

/**
 * FiniteSetTest is a glassbox test of the FiniteSet class.
 */
public class FiniteSetTest {

  ///////////////////////////////////////////////////////////////////////////
  /// FiniteSet.FiniteSet() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Test creating basic sets.
   */
  @Test
  public void testCreationEmptySet() {
    assertEquals(Arrays.asList(),
        FiniteSet.of(new float[0]).getPoints());
  }

  /**
   * Test creating basic sets.
   */
  @Test
  public void testCreationBasic() {
    assertEquals(Arrays.asList(1f),
        FiniteSet.of(new float[] {1}).getPoints());      // one item
    assertEquals(Arrays.asList(1f, 2f),
        FiniteSet.of(new float[] {1, 2}).getPoints());   // two items
    assertEquals(Arrays.asList(1f, 2f),
        FiniteSet.of(new float[] {2, 1}).getPoints());   // two out-of-order
  }

  /**
   * Test creating a set that contains a negative point.
   */
  @Test
  public void testCreationNegative() {
    assertEquals(Arrays.asList(-2f, 2f),
        FiniteSet.of(new float[] {2, -2}).getPoints());
  }

  // Note:
  // The following are sets used throughout the rest of the tests.

  /** An empty set. */
  private static FiniteSet S0 = FiniteSet.of(new float[0]);

  /** A "singleton" set. */
  private static FiniteSet S1 = FiniteSet.of(new float[] {1});

  /** A "complex" set. Or, a set that contains more than one value. */
  private static FiniteSet S12 = FiniteSet.of(new float[] {1, 2});

  // TODO: Feel free to initialize additional (private static) FiniteSet
  //       objects here if you plan to use more of them for the tests you
  //       need to implement below.

  private static FiniteSet S123 = FiniteSet.of(new float[] {3, 2, 1});

  private static FiniteSet S2 = FiniteSet.of(new float[] {2});

  private static FiniteSet set1 = FiniteSet.of(new float[] {-2, -1, 0});

  private static FiniteSet set2 = FiniteSet.of(new float[] {0, 1, 2});

  private static FiniteSet set3 = FiniteSet.of(new float[] {1, 2, 3});

  private static FiniteSet set4 = FiniteSet.of(new float[] {3, 4, 5});

  private static FiniteSet SmallNUm = FiniteSet.of(new float[] {(float) 1.111111, (float) 1.111112, (float) 1.111113});

  private static FiniteSet SmallNUm2 = FiniteSet.of(new float[] {(float) 1.111111, (float) 1.111114, (float) 1.111115});
  ///////////////////////////////////////////////////////////////////////////
  /// FiniteSet.equals() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Test set equality on an empty set.
   */
  @Test
  public void testEqualsEmptySet() {
    assertTrue(S0.equals(S0));
    assertFalse(S0.equals(S1));
    assertFalse(S0.equals(S12));
  }

  /**
   * Test set equality on a set containing one point.
   */
  @Test
  public void testEqualsSingleton() {
    assertFalse(S1.equals(S0));
    assertTrue(S1.equals(S1));
    assertFalse(S1.equals(S12));
  }

  /**
   * Test set equality on a set of multiple points.
   */
  @Test
  public void testEqualsComplexSet() {
    assertFalse(S12.equals(S0));
    assertFalse(S12.equals(S1));
    assertTrue(S12.equals(S12));
  }

  ///////////////////////////////////////////////////////////////////////////
  /// FiniteSet.size() Test
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Test set size.
   */
  @Test
  public void testSize() {
    assertEquals(S0.size(), 0);
    assertEquals(S1.size(), 1);
    assertEquals(S12.size(), 2);
  }

  ///////////////////////////////////////////////////////////////////////////
  /// FiniteSet.union() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests forming the union of two finite sets.
   */

  /**
   * Tests taking the union of an empty set.
   */
  @Test
  public void testUnionEmptySet() {
    assertTrue(S0.equals(S0.union(S0)));
    assertFalse(S0.equals(S0.union(S1)));
    assertTrue(S1.equals(S0.union(S1)));
  }

  /**
   * Tests taking the union of singleton set.
   */
  @Test
  public void testUnionSingleton() {
    assertTrue(S1.equals(S1.union(S0)));
    assertTrue(S1.equals(S1.union(S1)));
    assertFalse(S1.equals(S1.union(S12)));
  }

  /**
   * Tests taking the union of sets that contain multiple points.
   */
  @Test
  public void testUnionComplexSet() {
    assertTrue(S12.equals(S1.union(S12)));
    assertTrue(S12.equals(S0.union(S12)));
    assertFalse(S12.equals(S12.union(S123)));
    assertTrue(S12.equals(S1.union(S2)));
    assertFalse(S123.equals(S12.union(S1)));
    assertTrue(S123.equals(S12.union(S123)));
  }

  /**
   * Tests taking union of a set with itself.
   */
  @Test
  public void testUnionAliases() {
    assertTrue(S0.equals(S0.union(S0)));
    assertTrue(S1.equals(S1.union(S1)));
    assertTrue(S12.equals(S12.union(S12)));
    assertTrue(S123.equals(S123.union(S123)));
  }

  /**
   * Tests taking the union of overlapping sets.
   */
  @Test
  public void testUnionOverlapping() {
    assertTrue(FiniteSet.of(new float[] {-2,-1,0,1,2}).equals(set1.union(set2)));
    assertTrue(FiniteSet.of(new float[] {1,2,3,4,5}).equals(set3.union(set4)));
    assertFalse((Arrays.asList(1, 2, 3, 3, 4, 5)).equals(set3.union(set4).getPoints()));
  }

  /**
   * Tests taking the union of non overlapping sets.
   */
  @Test
  public void testUnionNonOverlapping() {
    assertTrue(FiniteSet.of(new float[] {-2,-1,0,1,2, 3}).equals(set1.union(set3)));
    assertTrue(FiniteSet.of(new float[] {0,1,2,3,4,5}).equals(set2.union(set4)));
  }
  /**
   *  Tests union on small Numbers
   */
  @Test
  public void testUnionSmallNum() {
    assertTrue(Arrays.asList((float) 1.111111, (float) 1.111112, (float) 1.111113,
            (float) 1.111114, (float) 1.111115).equals(SmallNUm.union(SmallNUm2).getPoints()));
  }
  ///////////////////////////////////////////////////////////////////////////
  /// FiniteSet.intersection() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests forming the intersection of two finite sets.
   */


  /**
   * Tests taking the union of an empty set.
   */
  @Test
  public void testIntersectionEmptySet() {
    assertTrue(S0.equals(S0.intersection(S0)));
    assertTrue(S0.equals(S0.intersection(S1)));
    assertTrue(S0.equals(S0.intersection(S123)));
    assertFalse(S1.equals(S1.intersection(S0)));
  }

  /**
   * Tests taking the union of singleton set.
   */
  @Test
  public void testIntersectionSingleton() {
    assertTrue(S1.equals(S1.intersection(S12)));
    assertTrue(S1.equals(S1.intersection(S123)));
    assertFalse(S1.equals(S1.intersection(S0)));
    assertFalse(S1.equals(S1.intersection(S2)));
  }

  /**
   * Tests taking the union of sets that contain multiple points.
   */
  @Test
  public void testIntersectionComplexSet() {
    assertTrue(S12.equals(S123.intersection(S12)));
    assertFalse(S12.equals(S0.intersection(S12)));
    assertFalse(S123.equals(S12.intersection(S123)));
    assertTrue(S1.equals(S1.intersection(S12)));
  }

  /**
   * Tests taking intersection of a set with itself.
   */
  @Test
  public void testIntersectionAliases() {
    assertTrue(S0.equals(S0.intersection(S0)));
    assertTrue(S1.equals(S1.intersection(S1)));
    assertTrue(S12.equals(S12.intersection(S12)));
    assertTrue(S123.equals(S123.intersection(S123)));
  }

  /**
   * Tests taking the union of overlapping sets.
   */
  @Test
  public void testIntersectionOverlapping() {
    assertTrue(FiniteSet.of(new float[] {0}).equals(set1.intersection(set2)));
    assertTrue(FiniteSet.of(new float[] {3}).equals(set3.intersection(set4)));
    assertFalse((Arrays.asList(1, 2, 3, 4, 5)).equals(set3.intersection(set4).getPoints()));
  }

  /**
   * Tests taking the union of non overlapping sets.
   */
  @Test
  public void testIntersectionNonOverlapping() {
    assertTrue(FiniteSet.of(new float[0]).equals(set1.intersection(set3)));
    assertTrue(FiniteSet.of(new float[0]).equals(set2.intersection(set4)));
    assertFalse((Arrays.asList(1)).equals(set2.intersection(set4).getPoints()));
  }

  /**
   *  Tests intersection on small Numbers
   */
  @Test
  public void testIntersectionSmallNum() {
    assertTrue(Arrays.asList((float) 1.111111).equals(SmallNUm.intersection(SmallNUm2).getPoints()));
  }
  ///////////////////////////////////////////////////////////////////////////
  /// FiniteSet.difference() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests forming the difference of two finite sets.
   */

  /**
   * Tests taking the union of an empty set.
   */
  @Test
  public void testDifferenceEmptySet() {
    assertTrue(S0.equals(S0.difference(S0)));
    assertTrue(S0.equals(S0.difference(S1)));
    assertTrue(S0.equals(S0.difference(S123)));
    assertTrue(S1.equals(S1.difference(S0)));
    assertFalse(S0.equals(S1.difference(S0)));
  }

  /**
   * Tests taking the union of singleton set.
   */
  @Test
  public void testDifferenceSingleton() {
    assertTrue(S1.equals(S1.difference(S2)));
    assertFalse(S1.equals(S2.difference(S1)));
    assertTrue(S0.equals(S1.difference(S123)));
    assertTrue(S1.equals(S1.difference(S0)));

  }

  /**
   * Tests taking the union of sets that contain multiple points.
   */
  @Test
  public void testDifferenceComplexSet() {
    assertTrue(S12.equals(S12.difference(S0)));
    assertFalse(S12.equals(S0.difference(S12)));
    assertTrue(S123.equals(S123.difference(S0)));
    assertTrue(S2.equals(S12.difference(S1)));
  }

  /**
   * Tests taking intersection of a set with itself.
   */
  @Test
  public void testDifferenceAliases() {
    assertTrue(S0.equals(S0.difference(S0)));
    assertTrue(S0.equals(S1.difference(S1)));
    assertTrue(S0.equals(S12.difference(S12)));
    assertTrue(S0.equals(S123.difference(S123)));
  }

  /**
   * Tests taking the union of overlapping sets.
   */
  @Test
  public void testDifferenceOverlapping() {
    assertTrue(FiniteSet.of(new float[] {-2, -1}).equals(set1.difference(set2)));
    assertTrue(FiniteSet.of(new float[] {1, 2}).equals(set2.difference(set1)));
    assertTrue(FiniteSet.of(new float[] {1, 2}).equals(set3.difference(set4)));
    assertTrue(FiniteSet.of(new float[] {4, 5}).equals(set4.difference(set3)));
    assertFalse((Arrays.asList(1, 2, 3)).equals(set3.difference(set4).getPoints()));
  }

  /**
   * Tests taking the union of non overlapping sets.
   */
  @Test
  public void testDifferenceNonOverlapping() {
    assertTrue(set1.equals(set1.difference(set3)));
    assertTrue(set3.equals(set3.difference(set1)));
    assertTrue(set2.equals(set2.difference(set4)));
    assertTrue(set4.equals(set4.difference(set2)));
    assertFalse((Arrays.asList(3)).equals(set2.difference(set4).getPoints()));
  }
  /**
   *  Tests difference on small Numbers
   */
  @Test
  public void testDifferenceSmallNum() {
    assertTrue(Arrays.asList((float) 1.111112, (float) 1.111113).equals(SmallNUm.difference(SmallNUm2).getPoints()));
  }
}
