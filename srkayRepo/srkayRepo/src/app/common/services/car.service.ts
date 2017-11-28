import { Injectable } from '@angular/core';
import { Car } from '../models/car';
import {TranslateService} from 'ng2-translate';

@Injectable()
export class CarService {

  constructor(private translate: TranslateService) { }

  getAllCars(): Car[] {
    let cars: Car[] = [];

    let car: Car = <Car>{};
    car.vin = "1";
    car.year = "2012";
    car.brand = "Maruti";
    car.color = "Gray";
    cars.push(car);

    let car1: Car = <Car>{};
    car1.vin = "2";
    car1.year = "2014";
    car1.brand = "Honda";
    car1.color = "Red";
    cars.push(car1);

    let car3: Car = <Car>{};
    car3.vin = "3";
    car3.year = "2012";
    car3.brand = "BMW";
    car3.color = "Gray";
    cars.push(car3);

    let car4: Car = <Car>{};
    car4.vin = "4";
    car4.year = "2016";
    car4.brand = "BMW";
    car4.color = "Green";
    cars.push(car4);

    let car5: Car = <Car>{};
    car5.vin = "5";
    car5.year = "2015";
    car5.brand = "Maruti";
    car5.color = "Yellow";
    cars.push(car5);

    let car6: Car = <Car>{};
    car6.vin = "6";
    car6.year = "2012";
    car6.brand = "Maruti";
    car6.color = "Gray";
    cars.push(car6);

    let car7: Car = <Car>{};
    car7.vin = "7";
    car7.year = "2014";
    car7.brand = "Honda";
    car7.color = "Red";
    cars.push(car7);

    let car8: Car = <Car>{};
    car8.vin = "8";
    car8.year = "2012";
    car8.brand = "BMW";
    car8.color = "Gray";
    cars.push(car8);

    let car9: Car = <Car>{};
    car9.vin = "9";
    car9.year = "2016";
    car9.brand = "BMW";
    car9.color = "Green";
    cars.push(car9);

    let car10: Car = <Car>{};
    car10.vin = "10";
    car10.year = "2015";
    car10.brand = "Maruti";
    car10.color = "Yellow";
    cars.push(car10);

    return cars;
  }

  translateCarObj(carList: any[]): Car[] {
    for (let i: number = 0; i < carList.length; i++) {
      let car: Car = carList[i];
      this.translateProperty('brand', car);
      this.translateProperty('color', car);
    }
    return carList;
  }

  translateProperty(prop: string, targetObj: any) {
    this.translate.get(targetObj[prop]).subscribe((res: string) => {
      targetObj[prop] = res;
    });
  }

}
