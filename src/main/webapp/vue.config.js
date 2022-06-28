const path = require("path");

module.exports = {
	outputDir: "dist-new",
	pages: {
		index: {
			entry: './src/main.js',
			// the source template
			template: './public/index.html',
			// output as dist/index.html
			filename: 'index.html',
			// when using title option,
			// template title tag needs to be <title><%= htmlWebpackPlugin.options.title %></title>
			title: 'Index Page',
			// chunks to include on this page, by default includes
			// extracted common chunks and vendor chunks.
			chunks: ['chunk-vendors', 'chunk-common', 'index']
		}
	}
}