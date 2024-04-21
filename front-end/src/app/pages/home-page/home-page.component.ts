import { Component } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { RecipeCardComponent } from '../recipe-card/recipe-card.component';
import {
  MatDialog,
} from '@angular/material/dialog';
import { CreateRecipeFormComponent } from '../create-recipe-form/create-recipe-form.component';
import { AuthService } from '../../services/auth.service';
import { RecipeService } from '../../services/recipe.service';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [MatIconModule, MatButtonModule, RecipeCardComponent],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss'
})
export class HomePageComponent {

  recipes = []

  constructor(
    public dialog:MatDialog,
    public authService:AuthService,
    private recipeService:RecipeService) {}

  handleOpenCreateRecipeForm() {
    this.dialog.open(CreateRecipeFormComponent)
  }

  ngOnInit() {
    this.authService.getUserProfile().subscribe(
      {
        next:data=>console.log("req user",data),
        error:error=>console.log("error",error)
      }
    );
    this.recipeService.getRecipes().subscribe(
      data=>this.recipes=data
    )
    this.recipeService.recipeSubject.subscribe(
      (state)=>{
        console.log("state ",state)
        this.recipes=state.recipes
      }
    )
  }
}
