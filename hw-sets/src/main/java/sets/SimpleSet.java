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

import java.util.List;

/**
 * Represents an immutable set of points on the real line that is easy to
 * describe, either because it is a finite set, e.g., {p1, p2, ..., pN}, or
 * because it excludes only a finite set, e.g., R \ {p1, p2, ..., pN}. As with
 * FiniteSet, each point is represented by a Java float with a non-infinite,
 * non-NaN value.
 */
public class SimpleSet {

  // RI: (inSet != null and notInSet = null) or (inSet = null and notInSet != null)

  // AF(this): if the set is finite, then inSet = all the points in set, notInSet = null
  //     if the set is infinite, then notInSet = all the points not in the set, inSet = null
  private final FiniteSet inSet;
  private final FiniteSet notInSet;

  /**
   * Creates a simple set containing only the given points.
   * @param vals Array containing the points to make into a SimpleSet
   * @spec.requires points != null and has no NaNs, no infinities, and no dups
   * @spec.effects this = {vals[0], vals[1], ..., vals[vals.length-1]}
   */
  public SimpleSet(float[] vals) {
    this.inSet = FiniteSet.of(vals);
    this.notInSet = null;
  }

  private SimpleSet(List<Float> vals) {
    float[] newPoints = new float[vals.size()];
    for (int i = 0; i < vals.size(); i++) {
      newPoints[i] = vals.get(i);
    }
    this.inSet = FiniteSet.of(newPoints);
    this.notInSet = null;
  }
  private SimpleSet(List<Float> vals, String infinite) {
    float[] newPoints = new float[vals.size()];
    for (int i = 0; i < vals.size(); i++) {
      newPoints[i] = vals.get(i);
    }
    this.inSet = null;
    this.notInSet = FiniteSet.of(newPoints);
  }
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SimpleSet))
      return false;

    SimpleSet other = (SimpleSet) o;
    if (other.inSet != null && this.inSet != null) {
      return this.inSet.equals(other.inSet);
    } else if (other.notInSet != null && this.notInSet != null) {
      return this.notInSet.equals(other.notInSet);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Returns the number of points in this set.
   * @return N      if this = {p1, p2, ..., pN} and
   *         infty  if this = R \ {p1, p2, ..., pN}
   */
  public float size() {
    // if the set is finite, which is when inSet != null and noInSet = null, then we return
    // the size of the FiniteSet inSet size, which is all the points in our set.
    // if the set is infinite, which is when inSet = null and notInset != null, then we return
    // ininity, since it includes all real numbers except those notInSet.
    if (this.inSet != null) {
      return this.inSet.size();
    }
    return Float.POSITIVE_INFINITY;
  }

  /**
   * Returns a string describing the points included in this set.
   * @return the string "R" if this contains every point,
   *     a string of the form "R \ {p1, p2, .., pN}" if this contains all
   *        but {@literal N > 0} points, or
   *     a string of the form "{p1, p2, .., pN}" if this contains
   *        {@literal N >= 0} points,
   *     where p1, p2, ... pN are replaced by the individual numbers. These
   *     floats will be turned into strings in the standard manner (the same as
   *     done by, e.g., String.valueOf(float)).
   */
  public String toString() {
    if (this.inSet != null) {
      List<Float> points = this.inSet.getPoints();
      StringBuilder string = new StringBuilder();
      int i = 0;
      // Inv: string = p1, p2, ,,, pi-1
      while (i < points.size()-1) {
        string.append(points.get(i) + ", ");
        i++;
      }
      if (points.size() > 0) {
        string.append(String.valueOf(points.get(i)));
      }
      string.insert(0, "{");
      string.append("}");
      return string.toString();

    } else {
      if (this.notInSet.size() == 0) {
        return "R";
      } else {
        List<Float> points = this.notInSet.getPoints();
        StringBuilder string = new StringBuilder();
        int i = 0;
        // Inv: string = p1, p2, ,,, pi-1
        while (i < points.size()-1) {
          string.append(String.valueOf(points.get(i)) + ", ");
          i++;
        }
        string.append(String.valueOf(points.get(i)));
        string.insert(0, "R \\ {");
        string.append("}");
        System.out.println(string.toString());
        return string.toString();
      }
    }
  }

  /**
   * Returns a set representing the points R \ this.
   * @return R \ this
   */
  public SimpleSet complement() {
    // if the set is infinite, which is when inSet = null and noInSet != null, then we return
    // a finite SimpleSet of the points in notInSet, since the compliment of R \ (R \ notInset) = notInset,
    // where R is all real numbers, R \ notInset, all real numbers except notInSet.

    // if the set is finite, which is when inSet != null and noInSet = null, then we return an
    // infinite SimpleSet not including the points/elements in inSet, since R \ (inSet) is all
    // real numbers excluding (inSet).
    //
    if (this.inSet == null) {
      return new SimpleSet(this.notInSet.getPoints());
    } else {
      return new SimpleSet(this.inSet.getPoints(), "true");
    }
  }

  /**
   * Returns the union of this and other.
   * @param other Set to union with this one.
   * @spec.requires other != null
   * @return this union other
   */
  public SimpleSet union(SimpleSet other) {

    // If both sets are finite, then we return the union of the two SimpleSet

    // If both sets are infinite, then we take the intersection of the points not
    // in this set and not in other set to get all (newPoints) = points not present in both infinite sets,
    // we return an infinite set excluding (newPoints)

    // If this is finite and other is infinite, then we take the difference of the points
    // not in other and the points in this set to get all (nePoints) = not present in both
    // this finite set and other infinite set, we return an infinite set excluding (newPoints)

    // If this is infinite and other is finite, then we take the difference of the points
    // not in this and the points in other set to get all (nePoints) = not present in both
    // this infinite set and other finite set, we return an infinite set excluding (newPoints)

    if (this.inSet != null && other.inSet != null) {
      List<Float> newPoints = this.inSet.union(other.inSet).getPoints();
      return new SimpleSet(newPoints);
    } else if (this.notInSet != null && other.notInSet != null){
      List<Float> newPoints = this.notInSet.intersection(other.notInSet).getPoints();
      return new SimpleSet(newPoints, "true");
    } else if (this.notInSet == null && other.inSet == null) {
      List<Float> newPoints = other.notInSet.difference(this.inSet).getPoints();
      return new SimpleSet(newPoints, "true");
    } else {
      List<Float> newPoints = this.notInSet.difference(other.inSet).getPoints();
      System.out.println(newPoints);
      return new SimpleSet(newPoints, "true");
    }
  }

  /**
   * Returns the intersection of this and other.
   * @param other Set to intersect with this one.
   * @spec.requires other != null
   * @return this intersected with other
   */
  public SimpleSet intersection(SimpleSet other) {

    // NOTE: There is more than one correct way to implement this.
    // If both sets are finite, we return the intersection of this and other finite set

    // If both this and other are infinte set, we take the union of the points not in this set
    // and points not other set to get all (newPoints) = not present in this infinite set or other
    // infinite set, we return an infinite set excluding (newPoints)

    // If this is a finite set and other is an infinite set, we take the difference of points in this set
    // and points not in other infinite set to get all (newPoints) =  points present in both set,
    // {here the difference gives us points  in this set and not in the excluded points of other
    // infinite set, which means the points in difference are present in both this and other, since
    // other also contains all points not in the excluded points}
    // we return a finite set including (newPoints)

    // If this is a infinite set and other is an finite set, we take the difference of points in other set
    // and points not in this infinite set to get all (newPoints) =  points present in both set,
    // {here the difference gives us points  in other set and not in the excluded points of this
    // infinite set, which means the points in difference are present in both other and this, since
    // this also contains all points not in the excluded points}
    // we return a finite set including (newPoints)


    if (this.inSet != null && other.inSet != null) {
      List<Float> newPoints = this.inSet.intersection(other.inSet).getPoints();
      return new SimpleSet(newPoints);
    } else if (this.notInSet != null && other.notInSet != null){
      List<Float> newPoints = this.notInSet.union(other.notInSet).getPoints();
      return new SimpleSet(newPoints, "true");
    } else if (this.inSet != null && other.inSet == null) {
      List<Float> newPoints = this.inSet.difference(other.notInSet).getPoints();
      return new SimpleSet(newPoints);
    } else {
      List<Float> newPoints = other.inSet.difference(this.notInSet).getPoints();
      return new SimpleSet(newPoints);
    }
  }

  /**
   * Returns the difference of this and other.
   * @param other Set to difference from this one.
   * @spec.requires other != null
   * @return this minus other
   */
  public SimpleSet difference(SimpleSet other) {

    // NOTE: There is more than one correct way to implement this.
    // If this and other are finite sets, then we return the difference this and other

    // If both this and other are infinite sets, we take the difference of points not in the other
    // set and the points not in this set to get all (newPoints) = present in this and not in the
    // other set {the difference is the points in the excluded points of other but not in the excluded
    // points of this, so we get points in this and not in other},
    // we return a finite set including (newPoints)

    // If this is a finite set and other is infinite set, we take the intersection of points
    // not in the set of other and points in this set to get all (newPoints)  = to get points present
    // in this set and not in the other set {the intersection is the points present in this and
    // the excluded points of other, so we get points in this and not in other}.
    // we return a finite set including (newPoints)

    // If this an infinite set and other is a finite set, we take the union of points
    // not in this set and points in other set to get all (newPoints) = present in the excluded
    // point of this or points in other set {the union is the points excluded from this set or
    // present in other set}, we return an infinite set excluding (newPoints)

    if (this.inSet != null && other.inSet != null) {
      List<Float> newPoints = this.inSet.difference(other.inSet).getPoints();
      return new SimpleSet(newPoints);
    } else if (this.notInSet != null && other.notInSet != null) {
      List<Float> newPoints = other.notInSet.difference(this.notInSet).getPoints();
      return new SimpleSet(newPoints);
    } else if (this.inSet != null && other.inSet == null) {
      List<Float> newPoints = other.notInSet.intersection(this.inSet).getPoints();
      return new SimpleSet(newPoints);
    } else {
      List<Float> newPoints = this.notInSet.union(other.inSet).getPoints();
      return new SimpleSet(newPoints, "");
    }
  }

}
