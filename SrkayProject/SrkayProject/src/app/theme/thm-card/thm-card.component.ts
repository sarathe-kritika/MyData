import {Component, ViewEncapsulation, ViewChild, Input} from '@angular/core';

@Component({
  selector: 'thm-card',
  templateUrl: './thm-card.component.html',
  styleUrls: ['./thm-card.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ThmCardComponent {
  @Input() title: String;
  @Input() thmCardClass: String;
}
