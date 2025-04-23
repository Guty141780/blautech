// src/app/components/products/products.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
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

  constructor(private svc: ProductService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.svc.getAll().subscribe({
      next: (list) => this.products = list,
      error: (err) => console.error('Error cargando productos', err)
    });
    this.resetForm();
  }

  resetForm(form?: NgForm): void {
    if (form) form.resetForm();
    this.model = { name: '', price: 0 };
    this.editMode = false;
  }

  submit(form: NgForm): void {
    if (this.editMode && this.model.id) {
      this.svc.update(this.model.id, { name: this.model.name, price: this.model.price })
        .subscribe(() => this.load());
    } else {
      this.svc.create({ name: this.model.name, price: this.model.price })
        .subscribe(() => this.load());
    }
  }

  edit(p: Product, form: NgForm): void {
    this.editMode = true;
    // Clonamos para no mutar directamente
    this.model = { ...p };
  }

  remove(id: string): void {
    this.svc.delete(id).subscribe(() => this.load());
  }
}