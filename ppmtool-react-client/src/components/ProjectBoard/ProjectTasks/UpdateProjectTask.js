import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import classnames from 'classnames';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {
  getProjectTask,
  updateProjectTask
} from '../../../actions/backlogActions';

class UpdateProjectTask extends Component {
  constructor() {
    super();
    this.state = {
      id: '',
      projectSequence: '',
      summary: '',
      acceptanceCriterion: '',
      status: '',
      priority: '',
      dueDate: null,
      projectIdentifier: '',
      created_at: '',
      errors: {}
    };
  }

  componentDidMount() {
    const { backlog_id, pt_id } = this.props.match.params;
    this.props.getProjectTask(backlog_id, pt_id, this.props.history);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
    const {
      id,
      projectSequence,
      summary,
      acceptanceCriterion,
      status,
      priority,
      dueDate,
      projectIdentifier,
      created_at
    } = nextProps.project_task;

    this.setState({
      id,
      projectSequence,
      summary,
      acceptanceCriterion,
      status,
      priority,
      dueDate,
      projectIdentifier,
      created_at
    });
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();
    const updated_task = {
      id: this.state.id,
      projectSequence: this.state.projectSequence,
      summary: this.state.summary,
      acceptanceCriterion: this.state.acceptanceCriterion,
      status: this.state.status,
      priority: this.state.priority,
      dueDate: this.state.dueDate,
      projectIdentifier: this.state.projectIdentifier,
      created_at: this.state.created_at
    };

    this.props.updateProjectTask(
      this.state.projectIdentifier,
      this.state.projectSequence,
      updated_task,
      this.props.history
    );
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="add-PBI">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <Link
                to={`/projectBoard/${this.state.projectIdentifier}`}
                className="btn btn-light"
              >
                Back to Project Board
              </Link>
              <h4 className="display-4 text-center">Update Project Task</h4>
              <p className="lead text-center">
                Project Name: {this.state.projectIdentifier} | Project Task Id:{' '}
                {this.state.projectSequence}
              </p>

              <form onSubmit={e => this.onSubmit(e)}>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames('form-control form-control-lg', {
                      'is-invalid': errors.summary
                    })}
                    name="summary"
                    placeholder="Project Task summary"
                    value={this.state.summary}
                    onChange={e => this.onChange(e)}
                  />
                  {errors.summary && (
                    <div className="invalid-feedback">{errors.summary}</div>
                  )}
                </div>

                <div className="form-group">
                  <textarea
                    className="form-control form-control-lg"
                    placeholder="Acceptance Criterion"
                    name="acceptanceCriterion"
                    value={this.state.acceptanceCriterion}
                    onChange={e => this.onChange(e)}
                  ></textarea>
                </div>
                <h6>Due Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-lg"
                    name="dueDate"
                    value={this.state.dueDate}
                    onChange={e => this.onChange(e)}
                  />
                </div>
                <div className="form-group">
                  <select
                    className="form-control form-control-lg"
                    name="priority"
                    value={this.state.priority}
                    onChange={e => this.onChange(e)}
                  >
                    <option value={0}>Select Priority</option>
                    <option value={1}>High</option>
                    <option value={2}>Medium</option>
                    <option value={3}>Low</option>
                  </select>
                </div>

                <div className="form-group">
                  <select
                    className="form-control form-control-lg"
                    name="status"
                    value={this.state.status}
                    onChange={e => this.onChange(e)}
                  >
                    <option value="">Select Status</option>
                    <option value="TO_DO">TO DO</option>
                    <option value="IN_PROGRESS">IN PROGRESS</option>
                    <option value="DONE">DONE</option>
                  </select>
                </div>

                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

UpdateProjectTask.propTypes = {
  getProjectTask: PropTypes.func.isRequired,
  updateProjectTask: PropTypes.func.isRequired,
  project_task: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  project_task: state.backlog.project_task,
  errors: state.errors
});

export default connect(mapStateToProps, { getProjectTask, updateProjectTask })(
  UpdateProjectTask
);
