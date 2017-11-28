import { Injectable } from '@angular/core';
import { Diamond } from '../models/diamond';

@Injectable()
export class ApiService {

  private diamonds: Diamond[] = [];

  constructor() { }

  getDiamondProfiles(): Diamond[] {
    let diamond: Diamond = <Diamond>{};
    diamond.shape = 'Round';
    diamond.size = 11;
    diamond.color = 'Pink';
    diamond.quality = 'Hight';
    this.diamonds.push(diamond);

    let diamond1: Diamond = <Diamond>{};
    diamond1.shape = 'Square';
    diamond1.size = 12;
    diamond1.color = 'White';
    diamond1.quality = 'Medium';
    this.diamonds.push(diamond1);

    return this.diamonds;
  }

}
