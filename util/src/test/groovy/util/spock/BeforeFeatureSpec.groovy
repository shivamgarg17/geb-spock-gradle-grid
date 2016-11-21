package util.spock

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Shared
import spock.lang.Unroll

@Slf4j
class BeforeFeatureSpec extends Specification {

    @Shared setupCount = 0

    def setupFeature() {
        //only runs once, even if the feature iterates using a 'where' block
        log.info "reading a config file, connecting to a database, etc..."
        setupCount++
    }

    @Unroll
    @BeforeFeature("setupFeature")
    def "#myInt squared should equal #myInt times itself"() {

        expect: "the square of an Integer to equal the number times itself"
        //Just some dummy test context
        myInt**2 == myInt * myInt

        and: "we should only ever initialize once"
        //Here is the important assert
        setupCount == 1

        where:
        //Iterates the test, filling myInt with each value of the Array
        myInt << [1, 2, 3, 4, 5]
    }
}
