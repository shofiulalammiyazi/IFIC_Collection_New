<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <!-- Editor JS -->
	<script src="editor/ckeditor.js"></script>
	<script src="editor/sample.js"></script>
	<!-- <link rel="stylesheet" href="editor/sample.css"> -->
	<!-- Editor JS -->
</head>
<body>
	<?php
    if( isset( $_POST[ 'tt' ] ) ) {
        if( !empty( $_POST[ 'editor1' ] ) ) {
            echo $_POST[ 'editor1' ] ;
            echo "</br>";
        }
    }
    ?>
	<form action="" method="post">
	
		<textarea cols="80" id="editor1" name="editor1" rows="10" placeholder="whire anything with details here"></textarea>
		<script>

			CKEDITOR.replace( 'editor1', {
				fullPage: true,
				allowedContent: true,
				extraPlugins: 'wysiwygarea'
			});

		</script>
		<p>
			<input type="submit" value="Submit" name="tt" />
		</p>
	</form>
	
</body>
</html>
