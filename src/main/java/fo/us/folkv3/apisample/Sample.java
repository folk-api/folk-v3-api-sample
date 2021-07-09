package fo.us.folkv3.apisample;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import fo.us.folkv3.api.client.FolkApiException;
import fo.us.folkv3.api.client.FolkClient;
import fo.us.folkv3.api.client.HeldinConfig;
import fo.us.folkv3.api.client.PersonMediumClient;
import fo.us.folkv3.api.client.PersonSmallClient;
import fo.us.folkv3.api.client.PrivateCommunityClient;
import fo.us.folkv3.api.client.PublicCommunityClient;
import fo.us.folkv3.api.model.Address;
import fo.us.folkv3.api.model.Changes;
import fo.us.folkv3.api.model.CommunityPerson;
import fo.us.folkv3.api.model.Guardian;
import fo.us.folkv3.api.model.HouseNumber;
import fo.us.folkv3.api.model.PersonMedium;
import fo.us.folkv3.api.model.PersonSmall;
import fo.us.folkv3.api.model.PrivateId;
import fo.us.folkv3.api.model.Ptal;
import fo.us.folkv3.api.model.PublicId;
import fo.us.folkv3.api.model.param.AddressParam;
import fo.us.folkv3.api.model.param.NameParam;

public class Sample {

	private final HeldinConfig config;
	private PersonSmallClient smallClient;
	private PersonMediumClient mediumClient;
	private PrivateCommunityClient privateCommunityClient;
	private PublicCommunityClient publicCommunityClient;
	
	public Sample(HeldinConfig config) {
		this.config = config;
	}

	public static void main(String[] args) {
		// For non secure consumer host:
//		var config = HeldinConfig.host("1.2.3.4")
//				.fo().dev().com().memberCode("123456").subSystemCode("sub-system");
		// For secure consumer host:
//		var config = HeldinConfig.secureHost("1.2.3.4")
//				.fo().dev().com().memberCode("123456").subSystemCode("sub-system");
		// With userId:
//		var config = HeldinConfig.secureHost("1.2.3.4")
//				.fo().dev().com().memberCode("123456").subSystemCode("sub-system")
//				.withUserId("user-id");
		// Create Sample instance:
//		var sample = new Sample(config);
		// Call test methods applicable for your consumer.
	}

	private void call(Runnable method) {
		try {
			method.run();
		} catch (FolkApiException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println();
		}
	}
	
	private void testSmallMethods() {
		call(() -> testGetPersonSmallByPrivateId());
		call(() -> testGetPersonSmallByPtal());
		call(() -> testGetPersonSmallByNameAndAddress());
		call(() -> testGetPersonSmallByNameAndDateOfBirth());
	}

	private void testMediumMethods() {
		call(() -> testGetPersonMediumByPrivateId());
		call(() -> testGetPersonMediumByPublicId());
		call(() -> testGetPersonMediumByPtal());
		call(() -> testGetPersonMediumByNameAndAddress());
		call(() -> testGetPersonMediumByNameAndDateOfBirth());
	}

	private void testPrivateCommunityMethods() {
		call(() -> testGetPrivateChanges());
		call(() -> testAddPersonToCommunityByNameAndAddress());
		call(() -> testAddPersonToCommunityByNameAndDateOfBirth());
		call(() -> testRemovePersonFromCommunity());
		call(() -> testRemovePersonsFromCommunity());
	}

	private void testPublicCommunityMethods() {
		call(() -> testGetPublicChanges());
	}

	private PersonSmallClient smallClient() {
		if (smallClient == null) {
			smallClient = FolkClient.personSmall(config);
		}
		return smallClient;
	}
	
	private PersonMediumClient mediumClient() {
		if (mediumClient == null) {
			mediumClient = FolkClient.personMedium(config);
		}
		return mediumClient;
	}

	private PrivateCommunityClient privateCommunityClient() {
		if (privateCommunityClient == null) {
			privateCommunityClient = FolkClient.privateCommunity(config);
		}
		return privateCommunityClient;
	}

	private PublicCommunityClient publicCommunityClient() {
		if (publicCommunityClient == null) {
			publicCommunityClient = FolkClient.publicCommunity(config);
		}
		return publicCommunityClient;
	}

	
	// Test small methods
	
	private void testSmallGetMyPrivileges() {
		System.out.println("# testSmallGetMyPrivileges");
		smallClient().getMyPrivileges().forEach(System.out::println);;
	}
	
	private void testGetPersonSmallByPrivateId() {
		System.out.println("# testGetPersonSmallByPrivateId");
		var person = smallClient().getPerson(
				PrivateId.of(1)
				);
		printPerson(person);
	}

	private void testGetPersonSmallByPtal() {
		System.out.println("# testGetPersonSmallByPtal");
		var person = smallClient().getPerson(
				Ptal.of("300408-559")
				);
		printPerson(person);
	}

	private void testGetPersonSmallByNameAndAddress() {
		System.out.println("# testGetPersonSmallByNameAndAddress");
		var person = smallClient().getPerson(
				NameParam.of("Karius", "Davidsen"),
				AddressParam.of("Úti í Bø",
						HouseNumber.of(16),
						"Syðrugøta")
				);
		printPerson(person);
	}

	private void testGetPersonSmallByNameAndDateOfBirth() {
		System.out.println("# testGetPersonSmallByNameAndDateOfBirth");
		var person = smallClient().getPerson(
				NameParam.of("Karius", "Davidsen"),
				LocalDate.of(2008, 4, 30)
				);
		printPerson(person);
	}

	
	// Test medium methods
	
	private void testMediumGetMyPrivileges() {
		System.out.println("# testMediumGetMyPrivileges");
		mediumClient().getMyPrivileges().forEach(System.out::println);;
	}
	
	private void testGetPersonMediumByPrivateId() {
		System.out.println("# testGetPersonMediumByPrivateId");
		var person = mediumClient().getPerson(
				PrivateId.of(1)
				);
		printPerson(person);
	}

	private void testGetPersonMediumByPublicId() {
		System.out.println("# testGetPersonMediumByPublicId");
		var person = mediumClient().getPerson(
				PublicId.of(1157442)
				);
		printPerson(person);
	}

	private void testGetPersonMediumByPtal() {
		System.out.println("# testGetPersonMediumByPtal");
		var person = mediumClient().getPerson(
				Ptal.of("300408559")
				);
		printPerson(person);
	}

	private void testGetPersonMediumByNameAndAddress() {
		System.out.println("# testGetPersonMediumByNameAndAddress");
		var person = mediumClient().getPerson(
				NameParam.of("Karius", "Davidsen"),
				AddressParam.of("Úti í Bø",	HouseNumber.of(16),	"Syðrugøta")
				);
		printPerson(person);
	}

	private void testGetPersonMediumByNameAndDateOfBirth() {
		System.out.println("# testGetPersonMediumByNameAndDateOfBirth");
		var person = mediumClient().getPerson(
				NameParam.of("Karius", "Davidsen"),
				LocalDate.of(2008, 4, 30)
				);
		printPerson(person);
	}

	
	// Test community methods
	
	private void testGetPrivateChanges() {
		System.out.println("# testGetPrivateChanges");
		Changes<PrivateId> changes = privateCommunityClient().getChanges(LocalDateTime.now().minusWeeks(1));
		System.out.printf("Changes - from: %s; to: %s; ids: %s%n%n", changes.getFrom(), changes.getTo(), changes.getIds());
	}
	
	private void testGetPublicChanges() {
		System.out.println("# testGetPublicChanges");
		Changes<PublicId> changes = publicCommunityClient().getChanges(LocalDateTime.now().minusWeeks(1));
		System.out.printf("Changes - from: %s; to: %s; ids: %s%n%n", changes.getFrom(), changes.getTo(), changes.getIds());
	}

	private void testAddPersonToCommunityByNameAndAddress() {
		System.out.println("# testAddPersonToCommunityByNameAndAddress");
		var communityPerson = privateCommunityClient().addPersonToCommunity(
				NameParam.of("Karius", "Davidsen"),
				AddressParam.of("Úti í Bø",
						HouseNumber.of(16),
						"Syðrugøta")
				);
		printCommunityPerson(communityPerson);
	}

	private void testAddPersonToCommunityByNameAndDateOfBirth() {
		System.out.println("# testAddPersonToCommunityByNameAndDateOfBirth");
		var communityPerson = privateCommunityClient().addPersonToCommunity(
				NameParam.of("Karius", "Davidsen"),
				LocalDate.of(2008, 4, 30)
				);
		printCommunityPerson(communityPerson);
	}

	private void testRemovePersonFromCommunity() {
		System.out.println("# testRemovePersonFromCommunity");
		var removedId = privateCommunityClient().removePersonFromCommunity(PrivateId.of(1));
		System.out.printf("Removed id: %s%n%n", removedId);
	}
	
	private void testRemovePersonsFromCommunity() {
		System.out.println("# testRemovePersonsFromCommunity");
		var removedIds = privateCommunityClient().removePersonsFromCommunity(PrivateId.list(1, 2, 3));
		System.out.printf("Removed ids: %s%n%n", removedIds);
	}
	

	// Print methods
	
	private static void printPerson(PersonSmall person) {
		if (person == null) {
			System.out.println("Person was not found!");
		} else {
			System.out.println(personToString(person));
		}
		System.out.println();
	}

	private static void printCommunityPerson(CommunityPerson person) {
		if (person == null) {
			System.out.println("Oops!");
		} else {
			System.out.println(communityPersonToString(person));
		}
		System.out.println();
	}

	private static String personToString(PersonSmall person) {
		if (person instanceof PersonMedium) {
			var personPublic = (PersonMedium) person;
			return format(person.getPrivateId(), personPublic.getPublicId(), ptal(personPublic),
					person.getName(), addressToString(person), personPublic.getDateOfBirth(),
					civilStatusToString(personPublic), specialMarksToString(personPublic),
					incapacityToString(personPublic));
		}
		var deadOrAlive = person.isAlive() ? "ALIVE" : ("DEAD " + person.getDateOfDeath());
		return format(person.getPrivateId(), person.getName(), addressToString(person), deadOrAlive);
	}

	private static String communityPersonToString(CommunityPerson communityPerson) {
		String personString = null;
		if (communityPerson.getStatus().isAdded()) {
			personString = personToString(communityPerson.getPerson());
		}
		return format(communityPerson.getStatus(), communityPerson.getExistingId(), personString);
	}
	
	private static String addressToString(PersonSmall person) {
		return addressToString(person.getAddress());
	}

	private static String addressToString(Address address) {
		return address.hasStreetAndNumbers()
						? address.getStreetAndNumbers()
								+ "; " + address.getCountry().getCode() + address.getPostalCode()
								+ " " + address.getCity()	
								+ "; " + address.getCountry().getNameFo()
								+ " (from: " + address.getFrom() + ')'
						: null;
	}
	
	private static String civilStatusToString(PersonMedium person) {
		if (person.getCivilStatus() == null) {
			return null;
		}
		return person.getCivilStatus().getType() + ", " + person.getCivilStatus().getFrom();
	}

	private static String ptal(PersonMedium person) {
		return person.getPtal() == null ? null : person.getPtal().getFormattedValue();
	}
	
	private static String specialMarksToString(PersonMedium person) {
		return person.getSpecialMarks().isEmpty()
				? null : person.getSpecialMarks().stream().map(Object::toString).collect(Collectors.joining());
	}

	private static String incapacityToString(PersonMedium person) {
		if (person.getIncapacity() == null) {
			return null;
		}
		var guardian1 = guardianToString(person.getIncapacity().getGuardian1());
		var guardian2 = guardianToString(person.getIncapacity().getGuardian2());
		return guardian2 == null ? guardian1 : guardian1 + " / " + guardian2;
	}

	private static String guardianToString(Guardian guardian) {
		if (guardian == null) {
			return null;
		}
		return guardian.getName() + " - " + addressToString(guardian.getAddress());
	}
	
	private static String format(Object... values) {
		return Arrays.stream(values)
				.map(v -> v == null ? "-" : v.toString())
				.collect(Collectors.joining(" | "));
	}
	
}
