/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-Present by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
#ifdef USE_TI_CALENDAR
@import JavaScriptCore;
@import TitaniumKit.ObjcProxy;

@class EKAlarm; // forward declare
@class CalendarModule;

@protocol TiCalendarAlertExports <JSExport>

// properties (and accessors)
PROPERTY(NSDate *, absoluteDate, AbsoluteDate);
//READONLY_PROPERTY(NSDate *, alarmTime, AlarmTime); // Not implemented on iOS
//READONLY_PROPERTY(NSDate *, begin, Begin); // Not implemented on iOS
//READONLY_PROPERTY(NSDate *, end, End); // Not implemented on iOS
//READONLY_PROPERTY(NSString *, eventId, EventId); // Not implemented on iOS
PROPERTY(double, relativeOffset, RelativeOffset);

@end

@interface TiCalendarAlert : ObjcProxy <TiCalendarAlertExports> {

  @private
  CalendarModule *module;
  EKAlarm *alert;
}

- (id)initWithAlert:(EKAlarm *)alert_
             module:(CalendarModule *)module_;

- (EKAlarm *)alert;
@end

#endif
