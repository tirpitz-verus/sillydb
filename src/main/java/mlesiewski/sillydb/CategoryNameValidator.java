package mlesiewski.sillydb;

class CategoryNameValidator extends NameValidator {

    static String returnValidCategoryNameOrThrow(String name) {
        return new CategoryNameValidator().returnValidNameOrThrow(name);
    }

    @Override
    void onEmptyName() {
        throw new CategoryWithBadNameCannotBeCreated();
    }

    @Override
    void onBadCharacter(String offendingString) {
        throw new CategoryWithBadNameCannotBeCreated(offendingString);
    }
}
