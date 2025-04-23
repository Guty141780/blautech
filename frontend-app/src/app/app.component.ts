import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';  // Importa RouterOutlet

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],  // Agrega RouterOutlet aquí
  template: `<router-outlet></router-outlet>`
})
export class AppComponent {}