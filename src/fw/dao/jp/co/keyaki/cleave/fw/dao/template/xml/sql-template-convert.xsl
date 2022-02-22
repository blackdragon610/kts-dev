<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="xml" indent="yes" omit-xml-declaration="no" encoding="Shift_JIS" />

<!--
	<xsl:template match="/">
		<xsl:call-template name="recursive-debug" />
	</xsl:template>
	<xsl:template name="recursive-debug">
		<xsl:param name="node" select="."/>
		<xsl:choose>
			<xsl:when test="not($node)"/>
			<xsl:when test="$node[1]/self::*">
				<tag><xsl:text>element</xsl:text>:<xsl:value-of select="name()" /></tag>
			</xsl:when>
			<xsl:when test="$node[1]/self::text()">
				<tag><xsl:text>text</xsl:text>:<xsl:value-of select="current()" /></tag>
			</xsl:when>
			<xsl:when test="$node[1]/self::comment()">
				<tag><xsl:text>comment</xsl:text>:<xsl:value-of select="current()" /></tag>
			</xsl:when>
			<xsl:when test="$node[1]/self::processing-instruction()">
				<tag><xsl:text>PI</xsl:text>:<xsl:value-of select="current()" /></tag>
			</xsl:when>
			<xsl:when test="not($node[1]/parent::*)">
				<tag><xsl:text>root</xsl:text>:<xsl:value-of select="current()" /></tag>
			</xsl:when>
			<xsl:when test="count($node[1]|$node[1]/../namespace::*) = count($node[1]/../namespace::*)">
				<tag><xsl:text>ns</xsl:text>:<xsl:value-of select="current()" /></tag>
			</xsl:when>
			<xsl:when test="count($node[1]|$node[1]/../@*) = count($node[1]/../@*)">
				<tag><xsl:text>attribute</xsl:text>:<xsl:value-of select="current()" /></tag>
			</xsl:when>
		</xsl:choose>
		<xsl:for-each select="$node[1]/self::processing-instruction()">
			<xsl:call-template name="recursive-debug" />
		</xsl:for-each>
		<xsl:for-each select="$node[1]/../namespace::*">
			<xsl:call-template name="recursive-debug" />
		</xsl:for-each>
		<xsl:for-each select="$node[1]/../@*">
			<xsl:call-template name="recursive-debug" />
		</xsl:for-each>
		<xsl:for-each select="./child::node()">
			<xsl:call-template name="recursive-debug" />
		</xsl:for-each>
	</xsl:template>
 -->

	<xsl:template match="/sql-templates">
		<xsl:call-template name="recursive-node" />
	</xsl:template>

	<xsl:template name="recursive-node">

		<!-- http://bbs.flatworld.jp/node/1345 -->
		<xsl:param name="node" select="."/>
		<xsl:choose>
			<xsl:when test="not($node)"/>
			<xsl:when test="$node[1]/self::*">
				<xsl:choose>
					<xsl:when test="name() = 'sql-templates'">
						<xsl:call-template name="sql-templates" />
					</xsl:when>
					<xsl:when test="name() = 'sql-template'">
						<xsl:call-template name="sql-template" />
					</xsl:when>
					<xsl:when test="name() = 'if-not-equals'">
						<xsl:call-template name="if-not-equals" />
					</xsl:when>
					<xsl:when test="name() = 'if-equals'">
						<xsl:call-template name="if-equals" />
					</xsl:when>
					<xsl:when test="name() = 'if-not-null'">
						<xsl:call-template name="if-not-null" />
					</xsl:when>
					<xsl:when test="name() = 'if-null'">
						<xsl:call-template name="if-null" />
					</xsl:when>
					<xsl:when test="name() = 'sql-ref'">
						<xsl:call-template name="sql-ref" />
					</xsl:when>
					<xsl:when test="name() = 'static'">
						<xsl:call-template name="static" />
					</xsl:when>
				<!-- 20140313 伊東敦史 start -->
					<xsl:when test="name() = 'if-not-empty'">
						<xsl:call-template name="if-not-empty" />
					</xsl:when>
				<!-- 20140313 伊東敦史 end -->
					</xsl:choose>
			</xsl:when>
			<xsl:when test="$node[1]/self::text()">
				<xsl:call-template name="text" />
			</xsl:when>
			<xsl:when test="$node[1]/self::comment()">
				<xsl:call-template name="comment" />
			</xsl:when>
			<xsl:when test="$node[1]/self::processing-instruction()">
			</xsl:when>
			<xsl:when test="not($node[1]/parent::*)">
			</xsl:when>
			<xsl:when test="count($node[1] | $node[1]/../namespace::*) = count($node[1]/../namespace::*)">
			</xsl:when>
			<xsl:when test="count($node[1] | $node[1]/../@*) = count($node[1]/../@*)">
			</xsl:when>
		</xsl:choose>

	</xsl:template>

	<xsl:template name="sql-templates">
		<xsl:variable name="xsi:noNamespaceSchemaLocation" select="@xsi:noNamespaceSchemaLocation"/>
		<xsl:element name="sql-templates">
			<xsl:attribute name="xsi:noNamespaceSchemaLocation"><xsl:value-of select="$xsi:noNamespaceSchemaLocation" /></xsl:attribute>
			<xsl:for-each select="./child::node()">
				<xsl:call-template name="recursive-node" />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<xsl:template name="sql-template">
		<xsl:variable name="id" select="@id"/>
		<xsl:element name="sql-template">
			<xsl:attribute name="id"><xsl:value-of select="$id"/></xsl:attribute>
			<xsl:for-each select="./child::node()">
				<xsl:call-template name="recursive-node" />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<xsl:template name="if-not-equals">
		<xsl:variable name="varName" select="@varName"/>
		<xsl:variable name="value" select="@value"/>
		<xsl:element name="if-not-equals">
			<xsl:attribute name="varName"><xsl:value-of select="$varName" /></xsl:attribute>
			<xsl:attribute name="value"><xsl:value-of select="$value" /></xsl:attribute>
			<xsl:for-each select="./child::node()">
				<xsl:call-template name="recursive-node" />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<xsl:template name="if-equals">
		<xsl:variable name="varName" select="@varName"/>
		<xsl:variable name="value" select="@value"/>
		<xsl:element name="if-equals">
			<xsl:attribute name="varName"><xsl:value-of select="$varName" /></xsl:attribute>
			<xsl:attribute name="value"><xsl:value-of select="$value" /></xsl:attribute>
			<xsl:for-each select="./child::node()">
				<xsl:call-template name="recursive-node" />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<!-- 20140313 伊東敦史 start -->
	<xsl:template name="if-not-empty">
		<xsl:variable name="varName" select="@varName"/>
		<xsl:element name="if-not-empty">
			<xsl:attribute name="varName"><xsl:value-of select="$varName" /></xsl:attribute>
			<xsl:for-each select="./child::node()">
				<xsl:call-template name="recursive-node" />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	<!-- 20140313 伊東敦史 end -->

	<xsl:template name="if-not-null">
		<xsl:variable name="varName" select="@varName"/>
		<xsl:element name="if-not-null">
			<xsl:attribute name="varName"><xsl:value-of select="$varName" /></xsl:attribute>
			<xsl:for-each select="./child::node()">
				<xsl:call-template name="recursive-node" />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<xsl:template name="if-null">
		<xsl:variable name="varName" select="@varName"/>
		<xsl:element name="if-null">
			<xsl:attribute name="varName"><xsl:value-of select="$varName" /></xsl:attribute>
			<xsl:for-each select="./child::node()">
				<xsl:call-template name="recursive-node" />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<xsl:template name="sql-ref">
		<xsl:variable name="refId" select="@refId"/>
		<xsl:variable name="alias" select="string(@alias)"/>
		<xsl:element name="sql-ref">
			<xsl:attribute name="refId"><xsl:value-of select="$refId" /></xsl:attribute>
			<xsl:if test="$alias != ''">
				<xsl:attribute name="alias"><xsl:value-of select="$alias" /></xsl:attribute>
			</xsl:if>
		</xsl:element>
	</xsl:template>

	<xsl:template name="static">
		<xsl:element name="static"><xsl:value-of select="current()" /></xsl:element>
	</xsl:template>

	<xsl:template name="text">
		<xsl:if test="string-length(normalize-space(current())) > 0">
			<xsl:call-template name="static" />
		</xsl:if>
	</xsl:template>

	<xsl:template name="comment">
		<xsl:comment><xsl:value-of select="current()" /></xsl:comment>
	</xsl:template>

</xsl:stylesheet>