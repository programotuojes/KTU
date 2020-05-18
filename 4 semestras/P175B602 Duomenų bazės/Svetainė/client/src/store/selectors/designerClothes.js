import { createSelector } from 'reselect';

export const getDesignerClothes = createSelector(
  (state) => state.designerClothes.clothes,
  (clothes) => clothes
);

export const getDesignerClothingById = (index) => {
  return createSelector(
    (state) => state.designerClothes.clothes,
    (clothes) => clothes[index]
  );
};
