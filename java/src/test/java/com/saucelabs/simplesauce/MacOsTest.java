package com.saucelabs.simplesauce;

import com.saucelabs.simplesauce.enums.MacVersion;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(DataProviderRunner.class)
public class MacOsTest extends BaseConfigurationTest{
    @DataProvider
    public static Object[][] expectedMacOsVersions() {
        return new Object[][] {
                { MacVersion.Mojave, "macOS 10.14" },
                { MacVersion.HighSierra, "macOS 10.13" },
                { MacVersion.Sierra, "macOS 10.12" },
                { MacVersion.ElCapitan, "OS X 10.11" },
                { MacVersion.Yosemite, "OS X 10.10" }
        };
    }

    @Test
    @UseDataProvider("expectedMacOsVersions")
    public void withMacOs_returnsValidOsConfiguration(MacVersion version, String expectedMacOsVersion) {
        sauceOptions.withMac(version);
        sauce = instantiateSauceSession();

        sauce.start();
        String actualOsThatWasSet = getSessionPlatformString();
        assertEquals(expectedMacOsVersion, actualOsThatWasSet);
    }

    private String getSessionPlatformString() {
        return sauce.currentSessionCapabilities.getPlatform().toString();
    }

    @Test
    public void defaultSafari_browserVersionIs12_0() {
        sauceOptions.withSafari();
        sauce = instantiateSauceSession();

        sauce.start();

        //TODO mockSauceSession.sauceSessionCapabilities can be turned into a method, maybe on the session
        //class that allows easier access to the caps
        String safariVersionSetThroughSauceSession = sauce.currentSessionCapabilities.getVersion();
        assertEquals("latest", safariVersionSetThroughSauceSession);
    }
    @Test
    public void defaultSafari_macOsVersionIsMojave() {
        sauceOptions.withSafari();
        sauce = instantiateSauceSession();

        sauce.start();

        String safariVersionSetThroughSauceSession = getSessionPlatformString();
        assertEquals(Platforms.MAC_OS.MOJAVE, safariVersionSetThroughSauceSession);
    }
    @Test
    public void withSafari_browserName_setToSafari() {
        sauceOptions.withSafari(SafariVersion._8);
        sauce = instantiateSauceSession();

        sauce.start();

        String actualBrowserNameSetThroughSauceSession = sauce.currentSessionCapabilities.getBrowserName();
        assertEquals("safari", actualBrowserNameSetThroughSauceSession);
    }
    @Test
    public void withSafari_versionChangedFromDefault_returnsCorrectVersion() {
        sauceOptions.withSafari(SafariVersion._8);
        sauce = instantiateSauceSession();

        sauce.start();

        String actualBrowserVersionSetThroughSauceSession = sauce.currentSessionCapabilities.getVersion();
        assertEquals("8.0", actualBrowserVersionSetThroughSauceSession);
    }
    @Test
    public void withSafari_versionNotSet_returnsLatest() {
        sauceOptions.withSafari("");
        sauce = instantiateSauceSession();

        sauce.start();

        String actualBrowserVersionSetThroughSauceSession =
                sauce.currentSessionCapabilities.getVersion();
        assertEquals("latest", actualBrowserVersionSetThroughSauceSession);
    }
}
