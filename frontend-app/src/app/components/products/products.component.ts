import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { NGXLogger } from 'ngx-logger';

import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  model: Product = { name: '', price: 0 };
  editMode = false;

  constructor(private svc: ProductService, private logger: NGXLogger) {}

  ngOnInit(): void {
    this.logger.debug('ProductsComponent inicializado');
    this.load();
  }

  load(): void {
    this.logger.debug('Cargando lista de productos...');
    this.svc.getAll().subscribe({
      next: (list) => {
        this.products = list;
        this.logger.info('Productos cargados correctamente', list);
      },
      error: (err) => {
        this.logger.error('Error al cargar productos', err);
      }
    });
    this.resetForm();
  }

  resetForm(form?: NgForm): void {
    if (form) form.resetForm();
    this.model = { name: '', price: 0 };
    this.editMode = false;
    this.logger.debug('Formulario reiniciado');
  }

  submit(form: NgForm): void {
    if (this.editMode && this.model.id) {
      this.logger.debug('Enviando actualización del producto', this.model);
      this.svc.update(this.model.id, {
        name: this.model.name,
        price: this.model.price
      }).subscribe(() => {
        this.logger.info('Producto actualizado correctamente', this.model);
        this.load();
      });
    } else {
      this.logger.debug('Creando nuevo producto', this.model);
      this.svc.create({
        name: this.model.name,
        price: this.model.price
      }).subscribe(() => {
        this.logger.info('Producto creado correctamente', this.model);
        this.load();
      });
    }
  }

  edit(p: Product, form: NgForm): void {
    this.logger.debug('Editando producto', p);
    this.editMode = true;
    this.model = { ...p };
  }

  remove(id: string): void {
    this.logger.debug(`Eliminando producto con ID: ${id}`);
    this.svc.delete(id).subscribe(() => {
      this.logger.info(`Producto eliminado con ID: ${id}`);
      this.load();
    });
  }
}