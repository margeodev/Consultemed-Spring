$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event) {
	
	var button = $(event.relatedTarget) // PEGANDO O BOTAO QUE DISPARA O EVENTO
	var nome = button.data('nome') // PEGANDO OS DADOS DO CAMPO DATA DO LINK DE EXCLUSAO - NOME
	var id = button.data('id') // PEGANDO OS DADOS DO CAMPO DATA DO LINK DE EXCLUSAO - ID
	
	var modal  = $(this) // PEGANDO O MODAL
	var form   = modal.find('form'); //PEGANDO O FORM DO MODAL
	var action = form.data('url-base'); // PEGANDO A ACTION DO FORM DO MODAL - pegando a string do atributo do form
	
	if(!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action' , action + id);
	
	modal.find('.modal-body').html('Tem certeza que deseja excluir o usuário <strong> ' + nome + '</strong> ?')
	
})  