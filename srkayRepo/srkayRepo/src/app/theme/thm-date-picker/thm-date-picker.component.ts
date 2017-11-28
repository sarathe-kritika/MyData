import { Input, Output, EventEmitter, ViewChild, ElementRef, AfterViewInit, Component, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from "@angular/forms";
import 'jquery';
declare var jQuery: any;
export const DATE_PICKER_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => ThmDatePickerComponent),
  multi: true
};


@Component({
  selector: 'thm-date-picker',
  template: `<input #input type="text">`,
  styleUrls: ['./thm-date-picker.component.scss'],
  providers: [DATE_PICKER_VALUE_ACCESSOR]
})
export class ThmDatePickerComponent implements AfterViewInit, ControlValueAccessor {

  private onTouched = () => { };
  private onChange: (value: string) => void = () => { };

  @Input() value = '';
  @Output() dateChange = new EventEmitter();
  @ViewChild('input') input: ElementRef;

  constructor() { }

  writeValue(date: string) {
    this.value = date;
    jQuery(this.input.nativeElement).datetimepicker({ value: date });
  }

  registerOnChange(fn: any) {
    this.onChange = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouched = fn;
  }

  ngAfterViewInit() {
    jQuery.datetimepicker.setLocale('en');
    jQuery(this.input.nativeElement).datetimepicker({
      format: 'd-m-Y H:i',
      formatDate: 'd-m-Y',
      minDate: 0,
      //mask: true, // mask is causing issue in default year
      yearStart: new Date().getFullYear(),
      yearEnd: new Date().getFullYear() + 1,
      //  defaultSelect: true,
      closeOnWithoutClick: false,
      //  inline:true,
      disabledWeekDays: [0, 6],
      highlightedDates: ['14-01-2017,Makar Sakranti', '16-01-2017,Diwali Pooja ', '18-01-2017,Ganesh Mahotsav', '20-01-2017,New year holiday', '22-02-2017,Valentine Day'],
      disabledDates: ['14-01-2017', '16-01-2017', '18-01-2017', '20-01-2017', '22-02-2017'],
      allowTimes: ['12:00', '13:00', '15:00', '17:00', '17:05', '17:20', '19:00', '20:00'],
      // onChangeDateTime: (value) => {
      // },
      onSelectDate: (value) => {
        this.value = value;
        this.onChange(value);
        this.onTouched();
        this.dateChange.next(value);
      },
      onSelectTime: (value) => {
        this.value = value;
        this.onChange(value);
        this.onTouched();
        this.dateChange.next(value);
      }
    });
  }
}
