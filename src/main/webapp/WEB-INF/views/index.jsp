<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <title>Effective JPA With Spring Testing UI</title>

    <!--link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/3.4.1/build/cssreset/cssreset-min.css"-->
    <link href="/resources/styles/reset/cssreset-min.css"/>
    <link href="/resources/styles/style.css" rel="stylesheet">
    <link href="/resources/styles/template.css" rel="stylesheet">
    <link href="/resources/styles/radiusgroups.css" rel="stylesheet">
    <link href="/resources/styles/button.css" rel="stylesheet">
    <script type="text/javascript" src="/resources/js/jquery/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/common.js"></script>
    <script type="text/javascript" src="/resources/js/mustache/jquery.mustache.js"></script>
    <script type="text/javascript" src="/resources/js/icanhaz/icanhaz.min.js"></script>
</head>
<body>
    <header>
        <h1 class="">Effective JPA With Spring</h1>
        <nav class="">
            <ul>
                <li><a class="jpa" href="">JPA</a></li>
                <li><a class="springData" href="">Spring Data</a></li>
            </ul>
        </nav>
    </header>

    <section id="bodySection">

        <section id="jpa" class="radius15InfoBubble">
            <h1><b>JPA</b></h1>
            <form id="jpaForm">
                <section class="buttonRow">
                    <!--
                        Cascade one is none,
                        cascade two is all
                    -->
                    <a id="jpaCascadeButton1" class="button super" title="Test Cascade None." href="">Cascade None</a>
                    <a id="jpaCascadeButton2" class="button super" title="Test Cascade All." href="">Cascade All</a>
                </section>
                <section class="buttonRow">
                    <!--
                        CacheMode one is data not in cache for a find,
                        CacheMode two is data is in cache for find
                        CacheMode three is query with some data in cache
                    -->
                    <a id="jpaCacheModeButton1" class="button super" title="FirstLevelCache Miss." href="">L1 Cache Miss</a>
                    <a id="jpaCacheModeButton2" class="button super" title="Test FirstLevelCache Hit." href="">L1 Cache Hit</a>
                    <a id="jpaCacheModeButton3" class="button super" title="Test FirstLevelCache Query." href="">L1 Cache Query</a>
                </section>
                <section class="buttonRow">
                    <!--
                        Flush one is only commit
                        Flush two is do changes then query again
                        Flush three is transaction set to ReadOnly
                        Flush four is transaction set to ReadOnly, but update in code
                    -->
                    <a id="jpaFlushButton1" class="button super" title="Test Flush Commit." href="">Flush Commit</a>
                    <a id="jpaFlushButton2" class="button super" title="Test Flush Before Query." href="">Flush Error</a>
                    <a id="jpaFlushButton3" class="button super" title="Test Flush Read-Only." href="">Flush Changes</a>
                    <a id="jpaFlushButton4" class="button super" title="Test Flush Read-Only - update." href="">Flush RO - Update</a>
                </section>
                <section class="buttonRow">
                    <a id="jpaFindButton" class="button super" title="Test find/getReference." href="">find/getReference</a>
                    <a id="jpaFindAllButtonLazy" class="button super" title="Get Everyone Lazy." href="">Get All Lazy</a>
                    <a id="jpaFindAllButtonLazyError" class="button super" title="Get Everyone Lazy Error." href="">Get All Lazy Error</a>
                    <a id="jpaFindAllButtonEager" class="button super" title="Get Everyone Eager." href="">Get All Eager</a>
                </section>
            </form>
            <br />
            <form id="jpaSearchForm">
                <h1><b>Find by First Name</b></h1>
                <section class="formLine">
                    <label for="jpaSearchFirstName">Search Name: </label>
                    <input id="jpaSearchFirstName" type="text" name="firstName">
                </section>
                <a id="jpaFindByButton" class="button medium" title="Get people with this firstName." href="">Get</a>
            </form>
            <br />
            <form id="jpaZipSearchForm">
                <h1><b>Find by ZipCode</b></h1>
                <section class="formLine">
                    <label for="jpaSearchZipCode">Search Name: </label>
                    <input id="jpaSearchZipCode" type="text" name="zipCode">
                </section>
                <a id="jpaZipCodeFindByButton" class="button medium" title="Get people in this zip code." href="">Get</a>
            </form>
        </section>

        <section id="springData" class="radius15InfoBubble" style="display: none;">
            <h1><b>Spring Data</b></h1>
            <form id="springDataForm">
                <section class="formLine">
                    <a id="springDataFindAllButtonLazy" class="button medium" title="find all people." href="">Get Everyone Lazy</a>
                    <a id="springDataFindAllButtonEager" class="button medium" title="find all people." href="">Get Everyone Eager</a>
                </section>
            </form>
            <br />
            <form id="springDataSearchForm">
                <h1><b>Find by First Name</b></h1>
                <section class="formLine">
                    <label for="springDataSearchFirstName">Search Name: </label>
                    <input id="springDataSearchFirstName" type="text" name="firstName">
                </section>
                <a id="springDataFindByButton" class="button medium" title="Get people with this firstName." href="">Get</a>
            </form>
            <br />
            <form id="springDataZipSearchForm">
                <h1><b>Find by ZipCode</b></h1>
                <section class="formLine">
                    <label for="springDataSearchZipCode">Search Name: </label>
                    <input id="springDataSearchZipCode" type="text" name="zipCode">
                </section>
                <a id="springDataZipCodeFindByButton" class="button medium" title="Get people in this zip code." href="">Get</a>
            </form>
            <br />
        </section>
    </section>

    <section id="cleanResults" class="radius15InfoBubble">
        <a class="clearResults button medium" title="Clear Results." href="">Clear Results</a>
        <div id="resultsTemplates"></div>
    </section>

    <section id="results" class="radius15InfoBubble">
        <h2 class="resultsLabel"><b>results: </b></h2>
        <br />
        <div id="resultDiv"></div>
        <a class="clearResults button medium" title="Clear Results." href="">Clear Results</a>
    </section>

    <div id="templates" style="display: none;">
        <%@ include file="templates.jsp" %>
    </div>
</body>
</html>