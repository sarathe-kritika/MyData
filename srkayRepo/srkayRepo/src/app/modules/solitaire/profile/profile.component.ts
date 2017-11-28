import {Component } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  form = {
    date: ''
  };
  constructor() { }

  ngOnInit() {
  }

  onDateChange(date) {
    this.form.date = date;
  }
}
