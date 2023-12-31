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
 * SimpleSetTest is a glassbox test of the SimpleSet class.
 */
public class SimpleSetTest {

  ///////////////////////////////////////////////////////////////////////////
  /// SimpleSet.SimpleSet() Tests
  ///////////////////////////////////////////////////////////////////////////

  // Hint: If you are having a hard time debugging SimpleSet, you may want
  //       to create some tests for the constructor(s) that you've implemented
  //       in SimpleSet. See FiniteSetTest.java testCreation tests if you
  //       would like an example of how to test a constructor.

  // Note:
  // The following are sets used throughout the rest of the tests.

  /** An empty set. */
  private static SimpleSet S = new SimpleSet(new float[0]);

  /** "Singleton" sets. */
  private static SimpleSet S1 = new SimpleSet(new float[] {1});
  private static SimpleSet S2 = new SimpleSet(new float[] {2});
  private static SimpleSet S3 = new SimpleSet(new float[] {3});

  /** "Complex" sets. Or, sets that contain more than one value. */
  private static SimpleSet S12 = new SimpleSet(new float[] {1, 2});
  private static SimpleSet S13 = new SimpleSet(new float[] {1, 3});
  private static SimpleSet S23 = new SimpleSet(new float[] {2, 3});
  private static SimpleSet S123 = new SimpleSet(new float[] {1, 2, 3});

  // Note:
  // The following are complements of the above sets used throughout the
  // rest of the tests. Each set S has a corresponding R counterpart below.

  /** The complement of an empty set. */
  private static SimpleSet R = S.complement();

  /** Complements of "Singleton" sets. */
  private static SimpleSet R1 = S1.complement();
  private static SimpleSet R2 = S2.complement();
  private static SimpleSet R3 = S3.complement();

  /**
   * Complements of "Complex" sets. Or, sets that contain more than
   * one value.
   */
  private static SimpleSet R12 = S12.complement();
  private static SimpleSet R13 = S13.complement();
  private static SimpleSet R23 = S23.complement();
  private static SimpleSet R123 = S123.complement();

  ///////////////////////////////////////////////////////////////////////////
  /// SimpleSet.equals() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests equality on empty sets.
   */
  @Test
  public void testEqualsEmptySet() {
    assertTrue(S.equals(new SimpleSet(new float[0])));
    assertTrue(S.equals(S));
    assertFalse(S.equals(R));
    assertFalse(R.equals(S));
  }

  /**
   * Tests equality on sets that contain one point.
   */
  @Test
  public void testEqualsSingleton() {
    assertFalse(S1.equals(S));
    assertFalse(S1.equals(R));
    assertTrue(S1.equals(S1));
    assertTrue(R1.equals(R1));
    assertFalse(S1.equals(R1));
    assertFalse(R1.equals(S1));
    assertFalse(S1.equals(S12));
    assertFalse(S1.equals(S123));
  }

  /**
   * Tests equality on sets that multile points.
   */
  @Test
  public void testEqualsComplexSet() {
    assertFalse(S12.equals(S));
    assertFalse(S12.equals(R));
    assertTrue(S12.equals(S12));
    assertTrue(R12.equals(R12));
    assertFalse(S12.equals(R12));
    assertFalse(R12.equals(S12));
    assertFalse(S12.equals(S1));
    assertFalse(S12.equals(S123));
  }

  ///////////////////////////////////////////////////////////////////////////
  /// SimpleSet.size() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests calculating the size of a set.
   */

  /**
   * Tests size on empty sets.
   */
  @Test
  public void testSizeEmptySet() {
    assertEquals(S.size(), 0, 0.0001);
    assertEquals(R.size(), S.complement().size(), 0.0001);
    assertEquals(S.size(), R.complement().size(), 0.0001);
    assertEquals(R.size(), Float.POSITIVE_INFINITY, 0.0001);
  }

  /**
   * Tests taking the size of singleton sets.
   */
  @Test
  public void testSizeSingleton() {
    assertEquals(S1.size(), 1, 0.0001);
    assertEquals(R1.size(), Float.POSITIVE_INFINITY, 0.0001);
    assertEquals(R1.size(), S1.complement().size(), 0.0001);
    assertEquals(S1.size(), S2.size(), 0.0001);
    assertEquals(S2.size(), S3.size(), 0.0001);
  }

  /**
   * Tests the size of sets that contain multiple points.
   */
  @Test
  public void testSizeComplexSet() {
    assertEquals(S12.size(), 2, 0.0001);
    assertEquals(S13.size(), S12.size(), 0.0001);
    assertEquals(S123.size(), R123.complement().size(), 0.0001);
    assertEquals(S123.size(), S12.size()+1, 0.0001);
  }

  /**
   * Tests taking the complement of sets that contain multiple points.
   */
  @Test
  public void testSizeInfiniteSet() {
    assertEquals(R.size(), Float.POSITIVE_INFINITY, 0.0001);
    assertEquals(R13.size(), Float.POSITIVE_INFINITY, 0.0001);
    assertEquals(R123.size(), Float.POSITIVE_INFINITY, 0.0001);
    assertEquals(S12.complement().size(), Float.POSITIVE_INFINITY, 0.0001);
  }

  /**
   * Tests taking the complement of sets that contain multiple points.
   */
  @Test
  public void testSizeInfiniteComplex() {
    assertEquals(R.union(R2).size(), Float.POSITIVE_INFINITY, 0.0001);
    assertEquals(R13.complement().size(),S13.size() , 0.0001);
    assertEquals(R123.difference(S1).size(), Float.POSITIVE_INFINITY, 0.0001);
    assertEquals(S12.complement().size(), Float.POSITIVE_INFINITY, 0.0001);
  }

  ///////////////////////////////////////////////////////////////////////////
  /// SimpleSet.toString() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests converting a set to a string.
   */

  /**
   * Tests converting a empty set to a string.
   */
  @Test
  public void testToStringEmptySet() {
    assertTrue("{}".equals(S.toString()));
    assertTrue("{}".equals(R.complement().toString()));
  }

  /**
   * Tests converting a singleton set to a string.
   */
  @Test
  public void testToStringSingletonSet() {
    assertTrue("{1.0}".equals(S1.toString()));
    assertTrue("{1.0}".equals(R1.complement().toString()));
    assertTrue("{2.0}".equals(S2.toString()));
    assertTrue("{2.0}".equals(R2.complement().toString()));
  }

  /**
   * Tests converting a complex set to a string.
   */
  @Test
  public void testToStringComplexSet() {
    assertTrue("{1.0, 2.0}".equals(S12.toString()));
    assertTrue("{1.0, 2.0, 3.0}".equals(S123.toString()));
    assertTrue("{1.0, 3.0}".equals(S13.toString()));
    assertTrue("{1.0, 2.0}".equals(R12.complement().toString()));
  }

  /**
   * Tests converting an infinte set to a string.
   */
  @Test
  public void testToStringInfiniteSet() {
    assertTrue("R".equals(R.toString()));
    assertTrue("R".equals(S.complement().toString()));
  }

  /**
   * Tests converting an infinte set that contains all but 1 to a string.
   */
  @Test
  public void testToStringInfiniteSingletonSet() {
    assertTrue("R \\ {1.0}".equals(R1.toString()));
    assertTrue("R \\ {1.0}".equals(S1.complement().toString()));
    assertTrue("R \\ {2.0}".equals(R2.toString()));
    assertTrue("R \\ {2.0}".equals(S2.complement().toString()));
  }

  /**
   * Tests converting an infinte set that contains all points but set T to a string.
   */
  @Test
  public void testToStringInfiniteComplexSet() {
    assertTrue("R \\ {1.0, 2.0}".equals(R12.toString()));
    assertTrue("R \\ {1.0, 3.0}".equals(S13.complement().toString()));
    assertTrue("R \\ {1.0, 2.0, 3.0}".equals(R123.toString()));
    assertTrue("R \\ {1.0, 2.0}".equals(S12.complement().toString()));
  }

  ///////////////////////////////////////////////////////////////////////////
  /// SimpleSet.complement() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests taking the complement of an empty set.
   */
  @Test
  public void testComplementEmptySet() {
    assertTrue(R.equals(S.complement()));
    assertTrue(S.equals(R.complement()));
  }

  /**
   * Tests taking the complement of singleton sets.
   */
  @Test
  public void testComplementSingleton() {
    assertTrue(R1.equals(S1.complement()));
    assertTrue(R2.equals(S2.complement()));
    assertTrue(R3.equals(S3.complement()));

    assertTrue(S1.equals(R1.complement()));
    assertTrue(S2.equals(R2.complement()));
    assertTrue(S3.equals(R3.complement()));

    assertFalse(R12.equals(S1.complement()));
  }

  /**
   * Tests taking the complement of sets that contain multiple points.
   */
  @Test
  public void testComplementComplexSet() {
    assertTrue(R12.equals(S12.complement()));
    assertTrue(R13.equals(S13.complement()));
    assertTrue(R23.equals(S23.complement()));
    assertTrue(R123.equals(S123.complement()));

    assertTrue(S12.equals(R12.complement()));
    assertTrue(S13.equals(R13.complement()));
    assertTrue(S23.equals(R23.complement()));
    assertTrue(S123.equals(R123.complement()));

    assertFalse(R123.equals(S12.complement()));
    assertFalse(R1.equals(S123.complement()));
  }

  ///////////////////////////////////////////////////////////////////////////
  /// SimpleSet.union() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests forming the union of two sets.
   */
  @Test
  public void testUnion() {
    assertTrue(S.equals(S.union(S)));
    assertTrue(S1.equals(S1.union(S)));
    assertTrue(S1.equals(S1.union(S1)));
    assertTrue(S12.equals(S1.union(S2)));
    assertTrue(S12.equals(S12.union(S2)));
    assertTrue(S12.equals(S2.union(S12)));
    assertTrue(S123.equals(S12.union(S23)));
    assertTrue(S123.equals(S23.union(S12)));
  }

  /**
   * Tests forming the union of two sets that are both complements.
   */
  @Test
  public void testUnionComplements() {
    assertTrue(R.equals(R.union(R)));
    assertTrue(R.equals(R1.union(R)));
    assertTrue(R1.equals(R1.union(R1)));
    assertTrue(R.equals(R1.union(R2)));
    assertTrue(R2.equals(R12.union(R2)));
    assertTrue(R2.equals(R2.union(R12)));
    assertTrue(R2.equals(R12.union(R23)));
    assertTrue(R2.equals(R23.union(R12)));
  }

  /**
   * Tests forming the union of a set and a complement of a set.
   */
  @Test
  public void testUnionComplementAndNot() {
    assertTrue(R.equals(S.union(R)));
    assertTrue(R.equals(S1.union(R)));
    assertTrue(R.equals(S1.union(R1)));
    assertTrue(R2.equals(S1.union(R2)));
    assertTrue(R.equals(S12.union(R2)));
    assertTrue(R1.equals(S2.union(R12)));
    assertTrue(R3.equals(S12.union(R23)));
    assertTrue(R1.equals(S23.union(R12)));

    assertTrue(R.equals(R.union(S)));
    assertTrue(R1.equals(R1.union(S)));
    assertTrue(R.equals(R1.union(S1)));
    assertTrue(R1.equals(R1.union(S2)));
    assertTrue(R1.equals(R12.union(S2)));
    assertTrue(R.equals(R2.union(S12)));
    assertTrue(R1.equals(R12.union(S23)));
    assertTrue(R3.equals(R23.union(S12)));
  }

  ///////////////////////////////////////////////////////////////////////////
  /// SimpleSet.intersection() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests forming the intersection of two sets.
   */
  @Test
  public void testIntersection() {
    assertTrue(S.equals(S.intersection(S)));
    assertTrue(S.equals(S1.intersection(S)));
    assertTrue(S1.equals(S1.intersection(S1)));
    assertTrue(S.equals(S1.intersection(S2)));
    assertTrue(S2.equals(S12.intersection(S2)));
    assertTrue(S2.equals(S2.intersection(S12)));
    assertTrue(S2.equals(S12.intersection(S23)));
    assertTrue(S2.equals(S23.intersection(S12)));
  }

  /**
   * Tests forming the intersection of two sets that are both complements.
   */
  @Test
  public void testIntersectionComplements() {
    assertTrue(R.equals(R.intersection(R)));
    assertTrue(R1.equals(R1.intersection(R)));
    assertTrue(R1.equals(R1.intersection(R1)));
    assertTrue(R12.equals(R1.intersection(R2)));
    assertTrue(R12.equals(R12.intersection(R2)));
    assertTrue(R12.equals(R2.intersection(R12)));
    assertTrue(R123.equals(R12.intersection(R23)));
    assertTrue(R123.equals(R23.intersection(R12)));
  }

  /**
   * Tests forming the intersection of a set and a complement of a set.
   */
  @Test
  public void testIntersectionComplementAndNot() {
    assertTrue(S.equals(S.intersection(R)));
    assertTrue(S1.equals(S1.intersection(R)));
    assertTrue(S.equals(S1.intersection(R1)));
    assertTrue(S1.equals(S1.intersection(R2)));
    assertTrue(S1.equals(S12.intersection(R2)));
    assertTrue(S.equals(S2.intersection(R12)));
    assertTrue(S1.equals(S12.intersection(R23)));
    assertTrue(S3.equals(S23.intersection(R12)));

    assertTrue(S.equals(R.intersection(S)));
    assertTrue(S.equals(R1.intersection(S)));
    assertTrue(S.equals(R1.intersection(S1)));
    assertTrue(S2.equals(R1.intersection(S2)));
    assertTrue(S.equals(R12.intersection(S2)));
    assertTrue(S1.equals(R2.intersection(S12)));
    assertTrue(S3.equals(R12.intersection(S23)));
    assertTrue(S1.equals(R23.intersection(S12)));
  }

  ///////////////////////////////////////////////////////////////////////////
  /// SimpleSet.difference() Tests
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Tests forming the difference of two sets.
   */
  @Test
  public void testDifference() {
    assertTrue(S.equals(S.difference(S)));
    assertTrue(S1.equals(S1.difference(S)));
    assertTrue(S.equals(S1.difference(S1)));
    assertTrue(S1.equals(S1.difference(S2)));
    assertTrue(S1.equals(S12.difference(S2)));
    assertTrue(S.equals(S2.difference(S12)));
    assertTrue(S1.equals(S12.difference(S23)));
    assertTrue(S3.equals(S23.difference(S12)));
  }

  /**
   * Tests forming the difference of two sets that are both complements.
   */
  @Test
  public void testDifferenceComplements() {
    assertTrue(S.equals(R.difference(R)));
    assertTrue(S.equals(R1.difference(R)));
    assertTrue(S.equals(R1.difference(R1)));
    assertTrue(S2.equals(R1.difference(R2)));
    assertTrue(S.equals(R12.difference(R2)));
    assertTrue(S1.equals(R2.difference(R12)));
    assertTrue(S3.equals(R12.difference(R23)));
    assertTrue(S1.equals(R23.difference(R12)));
  }

  /**
   * Tests forming the difference of a set and a complement of a set.
   */
  @Test
  public void testDifferenceComplementAndNot() {
    assertTrue(S.equals(S.difference(R)));
    assertTrue(S.equals(S1.difference(R)));
    assertTrue(S1.equals(S1.difference(R1)));
    assertTrue(S.equals(S1.difference(R2)));
    assertTrue(S2.equals(S12.difference(R2)));
    assertTrue(S2.equals(S2.difference(R12)));
    assertTrue(S2.equals(S12.difference(R23)));
    assertTrue(S2.equals(S23.difference(R12)));

    assertTrue(R.equals(R.difference(S)));
    assertTrue(R1.equals(R1.difference(S)));
    assertTrue(R1.equals(R1.difference(S1)));
    assertTrue(R12.equals(R1.difference(S2)));
    assertTrue(R12.equals(R12.difference(S2)));
    assertTrue(R12.equals(R2.difference(S12)));
    assertTrue(R123.equals(R12.difference(S23)));
    assertTrue(R123.equals(R23.difference(S12)));
  }

}
