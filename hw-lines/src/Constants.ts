/*
 * Copyright (C) 2022 Kevin Zatloukal and James Wilcox.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

// Latitude of the UW Seattle campus
export const UW_LATITUDE : number = 47.65878405511131;

// Offset to translate coordinate system
export const UW_LATITUDE_OFFSET : number = 807.35188;

// Scale to translate coordinate system
export const UW_LATITUDE_SCALE : number = -0.00000576766;

// Longitude of the UW Seattle campus
export const UW_LONGITUDE : number = -122.31305164734569;

// Offset to translate coordinate system
export const UW_LONGITUDE_OFFSET : number = 1370.6408;

// Scale to translate coordinate system
export const UW_LONGITUDE_SCALE : number = 0.00000848028;

// Map center
export const UW_LATITUDE_CENTER = 47.65440627742146;

// Map center
export const UW_LONGITUDE_CENTER = -122.30476957834502;
// error message for invalid inputs
export const errorMessage: string[] = ['Minimum possible coordinate is (0, 0) and maximum possible coordinate is (4000, 4000)',
'Each row should be in the format x1 y1 x2 y2 COLOR', 'Enter a number (0, 4000) for x1 y1 x2 y1 y2 and Name of color for Color']
