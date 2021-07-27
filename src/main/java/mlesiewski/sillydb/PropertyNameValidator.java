package mlesiewski.sillydb;

class PropertyNameValidator extends NameValidator {

    static String returnValidPropertyNameOrThrow(String name) {
        return new PropertyNameValidator().returnValidNameOrThrow(name);
    }

    @Override
    void onEmptyName() {
        throw new BadPropertyNameCannotBeCreated();
    }

    @Override
    void onBadCharacter(String offendingString) {
        throw new BadPropertyNameCannotBeCreated(offendingString);
    }
}
