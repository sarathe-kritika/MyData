import { Component, OnInit } from '@angular/core';
import {Car} from "../../../common/models/car";
import {CarService} from "../../../common/services/car.service";
import {TranslateService} from 'ng2-translate';
import {SelectItem, Message} from 'primeng/primeng';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
  providers: [CarService]
})
export class TableComponent implements OnInit {

  private cars: Car[];

  private cars1: Car[];
  cols1: any[];

  cars2: Car[];
  cols2: any[];
  columnOptions2: SelectItem[];

  cars3: Car[];

  constructor(private carService: CarService, private translate: TranslateService) { }

  ngOnInit() {

    this.cars3 = this.carService.getAllCars();
    this.cars3 = this.carService.translateCarObj(this.cars3);


    this.cars2 = this.carService.getAllCars();
    this.cars2 = this.carService.translateCarObj(this.cars2);
    this.cols2 = [
      { field: 'vin', header: 'Vin' },
      { field: 'year', header: 'Year' },
      { field: 'brand', header: 'Brand' },
      { field: 'color', header: 'Color' }
    ];
    this.cols2 = this.translateColumns("header", this.cols2);
    this.columnOptions2 = [];
    for (let i = 0; i < this.cols2.length; i++) {
      this.columnOptions2.push({ label: this.cols2[i].header, value: this.cols2[i] });
    }
    this.columnOptions2 = this.translateColumns("label", this.columnOptions2);


    this.cars1 = this.carService.getAllCars();
    this.cars1 = this.carService.translateCarObj(this.cars1);
    this.cols1 = [
      { field: 'vin', header: 'Vin' },
      { field: 'year', header: 'Year' },
      { field: 'brand', header: 'Brand', filter: "true" },
      { field: 'color', header: 'Color' }
    ];
    this.cols1 = this.translateColumns("header", this.cols1);


    this.cars = this.carService.getAllCars();
    this.cars = this.carService.translateCarObj(this.cars);
  }

  translateColumns(prop: string, columns: any[]): any[] {
    for (let i: number = 0; i < columns.length; i++) {
      let propName: any = columns[i][prop];
      this.translate.get(propName, { value: '' }).subscribe((res: string) => {
        columns[i][prop] = res;
      });
    }
    return columns;
  }

}
