# Manual

Use this manual to guide you through the features of the application. You can also upload any relevant files or screenshots here to build a comprehensive application manual.

## Getting Started

- [ ] **Required:** Select at least one ingredient from the dropdown list.
  - Click the `+` button next to the **Ingredients** section.
  - You can also type in the ingredient name to search.
  - Watch the short instructional clip below:

    <img src="../img/ingredient.gif" alt="ingredient instruction" width="650" />

  
- [ ] **Optional:** Filter your results by selecting up to one region and one category.


- [ ] Click the search button `🔍` to generate recipe recommendations.


- [ ] Browse the results in the **Recipes** section.


- [ ] Click the `ⓘ` button in the bottom-right corner of a recipe card to view more details.


- [ ] **Download Feature:** If you like a recipe and want to save it, click the download button next to the recipe title.
  - Here's a short video guide on how to download:

    <img src="../img/download.gif" alt="download instruction" width="650" />



## 📽️ Video Walkthrough
<img src="../img/foodwizard.gif" alt="Video Walkthrough" />

## Implemented Features
### Required Features
- [x] Be able to view all items in the collection - in a logical order
  - In the user selection part, we display all the ingredients, regions, and categories in alphabetical order.

- [x] Be able to build a list of items from the collection
  - After user clicks the search button, a list of ingredients that user chose us built, as well as a list of recommended recipes.

- [x] Be able to save out that list using a file format we covered in the course such as .xml, .json, or .csv.
  - On the recipe detail page, user can download the recipe details as a JSON file by clicking the download button next to the recipe name.

### Additional Features
- [x] Be able to load in lists of items / previously saved lists, and modify them.  (Cache)
  - Implement cache to memorize previous user selection
- 
- [x] Be able to search for items in the collection
  - When selecting the ingredients, user can type in the ingredient name to search for it.

- [x] Be able to filter items in the collection
  - Allow user to select a specific category and region as additional filter.

- [x] Have your original item list come from an online API/online access.
  - We used 7 apis to get all the data.

- [x] Include images for your items
  - Display images for ingredients and recipes, and display video tutorial on recipe detail page.

- [x] Have the ability to modify a local copy of an item, and those modifications remain persistent across sessions. (Cache)
  - Implemented cache to memorize previous user selection
